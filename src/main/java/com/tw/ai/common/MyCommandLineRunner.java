package com.tw.ai.common;

import com.tw.ai.service.AiService;
import com.tw.ticket.service.TicketService;
import com.tw.trip.dao.TripDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripDAO tripDAO;
    private TicketService ticketService;
    private AiService aiService;

    public MyCommandLineRunner(TripDAO tripDAO, TicketService ticketService,AiService aiService) {
        this.tripDAO = tripDAO;
        this.ticketService = ticketService;
        this.aiService = aiService;
    }

    @Override
    public void run(String... args)  {
        System.out.println("伺服器部屬完畢時執行");
        var aiFavoriteFromMemberId = aiService.findAIFavoriteFromMemberId(3);
        System.out.println(aiFavoriteFromMemberId.toString());
    }
}
