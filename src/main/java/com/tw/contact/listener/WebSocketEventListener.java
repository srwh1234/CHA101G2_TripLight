package com.tw.contact.listener;

import com.tw.contact.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    // 使用SLF4J記錄器來記錄日誌
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    // SimpMessageSendingOperations是一個接口，提供了發送消息到WebSocket客戶端的方法
    private SimpMessageSendingOperations messagingTemplate;

    // 使用@Autowired註解來自動注入SimpMessageSendingOperations實例
    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messageSendingOperations){
        this.messagingTemplate = messageSendingOperations;
    }

    // 使用@EventListener註解來標記這個方法為事件監聽器，該方法會在收到WebSocket連接事件時被調用
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // 當收到新的WebSocket連接時，記錄一條日誌
        logger.info("Received a new web socket connection");
    }

    // 使用@EventListener註解來標記這個方法為事件監聽器，該方法會在收到WebSocket斷開事件時被調用
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // 使用StompHeaderAccessor來訪問STOMP協議的消息頭
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        // 從消息頭中獲取用戶名
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        // 如果用戶名不為空，則記錄一條用戶斷開連接的日誌，並發送一條用戶離開的消息到/topic/public目的地
        if(username != null) {
            logger.info("User Disconnected : " + username);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
