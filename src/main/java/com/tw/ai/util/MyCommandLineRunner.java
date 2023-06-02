package com.tw.ai.util;

import com.tw.ai.service.AiService;
import com.tw.ticket.service.TicketService;
import com.tw.trip.dao.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripRepository tripRepository;
    private TicketService ticketService;
    private AiService aiService;
    private final Logger logger
            = LoggerFactory.getLogger(this.getClass());

    public MyCommandLineRunner(TripRepository tripRepository, TicketService ticketService, AiService aiService) {
        this.tripRepository = tripRepository;
        this.ticketService = ticketService;
        this.aiService = aiService;
    }

    @Override
    public void run(String... args)  {
        logger.info("伺服器部屬完畢");
    }
}
