package com.tw.contact.controller;

import com.tw.contact.modelJPA.ChatRecord;
import com.tw.contact.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class Controller {


//    private ChatRecordService chatRecordService;
//
//    @Autowired
//    public Controller(ChatRecordService chatRecordService){
//        this.chatRecordService = chatRecordService;
//    }
//
//    @GetMapping("/ChatRecord")
//    public List<ChatRecord> getCR(){
//        return chatRecordService.findAll();
//    }
//
//    @GetMapping("ChatRecord/{id}")
//    public List<ChatRecord> getChatRecordByMemberId(@PathVariable int id){
//        return chatRecordService.findByMember(id);
//    }
//
//    @PutMapping
//    public void updateChatRecord(){
//
//    }
//
//
////    @GetMapping("/hello")
////    public String hello(){
////        return "hello";
////    }

}
