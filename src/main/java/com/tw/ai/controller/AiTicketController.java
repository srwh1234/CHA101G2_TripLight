package com.tw.ai.controller;

import com.tw.ai.service.TicketService;
import com.tw.ai.service.aiService.AiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiTicketController {
    private final TicketService ticketService;
    private final AiService aiService;

    @Autowired
    public AiTicketController(TicketService theticketService, AiService theaiService) {
        ticketService = theticketService;
        aiService = theaiService;
    }
    // 將推薦票券傳至前端  在表單送出時呼叫
    @GetMapping("/getTickets/{memberId}")
    @ResponseBody
    public String getTickets(@PathVariable("memberId") String memberId){
        var destination = aiService.getDestination(memberId);
        return ticketService.getTicket(destination);
    }
}