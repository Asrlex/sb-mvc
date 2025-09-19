package dev.api.springmvc.config;

import dev.api.springmvc.common.websocket.AuthenticatedWebSocketHandler;
import dev.api.springmvc.common.websocket.WebSocketSessionManager;
import dev.api.springmvc.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final JwtService jwtService;
	private final String apiKey;
	private final WebSocketSessionManager sessionManager;

	public WebSocketConfig(
			JwtService jwtService,
			@Value("${x-api-key}") String apiKey,
			WebSocketSessionManager sessionManager) {
		this.jwtService = jwtService;
		this.apiKey = apiKey;
		this.sessionManager = sessionManager;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(
				new AuthenticatedWebSocketHandler(jwtService, apiKey, sessionManager),"/ws/notifications")
				.setAllowedOrigins("*"); // adjust origins for production
	}
}
