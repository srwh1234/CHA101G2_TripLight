package com.tw.ai.common;

import com.tw.ai.service.AiService;
import com.tw.ticket.service.TicketService;
import com.tw.trip.dao.TripRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripRepository tripRepository;
    private TicketService ticketService;
    private AiService aiService;

    public MyCommandLineRunner(TripRepository tripRepository, TicketService ticketService, AiService aiService) {
        this.tripRepository = tripRepository;
        this.ticketService = ticketService;
        this.aiService = aiService;
    }

    @Override
    public void run(String... args)  {
        System.out.println("伺服器部屬完畢時執行");
    }
}
