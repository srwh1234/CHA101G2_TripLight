package com.tw.contact.controller;

import com.tw.contact.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {



    // @MessageMapping註解用於指定處理由客戶端發送到特定目的地的消息的方法。
    // 在這裡，該方法將處理所有發送到"/chat.sendMessage"目的地的消息。
    @MessageMapping("/chat.sendMessage")
    // @SendTo註解用於指定該方法返回的消息應該發送到哪個目的地。
    // 在這裡，返回的消息將被發送到"/topic/public"目的地。
    @SendTo("/topic/public")
    // 此方法接收一個ChatMessage對象作為載荷（payload），並直接返回這個對象。
    // 這意味著，當一個客戶端發送一個消息到"/chat.sendMessage"目的地時，該消息將被轉發到"/topic/public"目的地。
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
