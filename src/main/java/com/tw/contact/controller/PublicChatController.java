package com.tw.contact.controller;

import com.tw.contact.model.PublicChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class PublicChatController {



    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public PublicChatMessage sendMessage(@Payload PublicChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public PublicChatMessage addUser(@Payload PublicChatMessage chatMessage,
                                     SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
