package com.tw.contact.privateChat;

import jakarta.websocket.Session;
import lombok.Data;

@Data
public class SocketUserInfo {
    private String sessionId;

    private Session session;

    private String memberId;

    private String targetSessionId;

    private String userRole;
}

