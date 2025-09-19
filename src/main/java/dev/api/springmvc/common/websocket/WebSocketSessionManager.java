package dev.api.springmvc.common.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketSessionManager {

	private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

	public void addSession(WebSocketSession session) {
		sessions.add(session);
		log.info("Session added. Total: {}", sessions.size());
	}

	public void removeSession(WebSocketSession session) {
		sessions.remove(session);
		log.info("Session removed. Total: {}", sessions.size());
	}

	public void broadcast(String message) {
		sessions.forEach(session -> {
			if (session.isOpen()) {
				try {
					session.sendMessage(new TextMessage(message));
				} catch (Exception e) {
					log.error("Failed to send message", e);
				}
			}
		});
	}
}

