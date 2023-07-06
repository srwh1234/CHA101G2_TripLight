package com.tw.contact.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chat_records")
public class PrivateChatRecord {
    @Id
    private String id;
    private String chatRoomId;
//    private String user;
    private String customerName;
    private String chatContent;
    private LocalDateTime chatTime;
}
