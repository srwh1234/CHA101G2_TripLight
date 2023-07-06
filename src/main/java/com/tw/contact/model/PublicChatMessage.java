package com.tw.contact.model;

import lombok.Data;

@Data
public class PublicChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}
