package com.tw.form.controller;


import com.tw.ticket.controller.TicketController;
import com.tw.ticket.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexTicketController {

    private final TicketService ticketService;

    public IndexTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/index/tickets")
    public List<TicketController.DescTicketDto> getTicket(){
        return ticketService.getHotItem();
    }
}
