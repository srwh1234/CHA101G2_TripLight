package com.tw.ticket.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/TicketDetail")
public class TicketDetailMessage {

	private static Map<String, Session> sessions = new ConcurrentHashMap<>();

	public void send(final String jsonString) {
		for (final Session s : sessions.values()) {
			try {
				s.getBasicRemote().sendText(jsonString);
			} catch (final IOException e) {
				//
			}
		}
	}

	@OnOpen
	public void onOpen(final Session session) {
		sessions.put(session.getId(), session);
	}

	@OnClose
	public void onClose(final Session session) {
		sessions.remove(session.getId());

	}

	@OnMessage
	public void OnMessage(final String message) {
		//
		send(message);
	}

	@OnError
	public void onError(final Session session, final Throwable throwable) {
		try {
			session.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		throwable.printStackTrace();
	}
}
