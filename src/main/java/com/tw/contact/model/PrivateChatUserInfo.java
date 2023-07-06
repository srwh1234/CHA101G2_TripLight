package com.tw.contact.model;

import jakarta.websocket.Session;
import lombok.Data;

@Data
public class PrivateChatUserInfo {
    private String sessionId;

    private Session session;

    private String name;

    private String targetSessionId;

    private String userRole;
}

