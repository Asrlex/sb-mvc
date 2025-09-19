package dev.api.springmvc.common.websocket;

import dev.api.springmvc.security.JwtService;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class AuthenticatedWebSocketHandler extends TextWebSocketHandler {

	private final JwtService jwtService;
	private final String expectedApiKey;
	private final WebSocketSessionManager sessionManager;

	public AuthenticatedWebSocketHandler(
			JwtService jwtService,
			String expectedApiKey,
			WebSocketSessionManager sessionManager) {
		this.jwtService = jwtService;
		this.expectedApiKey = expectedApiKey;
		this.sessionManager = sessionManager;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		HttpHeaders headers = session.getHandshakeHeaders();
		String authHeader = headers.getFirst("Authorization");
		String apiKeyHeader = headers.getFirst("X-api-key");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			try {
				String token = authHeader.substring(7);
				String subject = jwtService.getSubject(token);
				log.info("WebSocket connected: JWT user {}", subject);
				sessionManager.addSession(session);
				return;
			} catch (Exception e) {
				session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Invalid JWT"));
				return;
			}
		}

		if (apiKeyHeader != null && apiKeyHeader.equals(expectedApiKey)) {
			log.info("WebSocket connected: API Key user");
			return;
		}

		session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Unauthorized"));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("Received: {}", message.getPayload());
		session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
	}

	@Override
	public void afterConnectionClosed(@Nonnull WebSocketSession session, @Nonnull CloseStatus status) {
		sessionManager.removeSession(session);
	}
}
