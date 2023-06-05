package com.tw.ai.util;

import com.tw.ai.dto.TripDto;
import com.tw.ai.service.AiService;
import com.tw.ai.service.TripService;
import com.tw.ticket.service.TicketService;
import com.tw.trip.model.Trip;
import com.tw.trip.repository.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripRepository tripRepository;
    private TicketService ticketService;
    private AiService aiService;

    private TripService tripService;
    private final Logger logger
            = LoggerFactory.getLogger(this.getClass());

    public MyCommandLineRunner(TripRepository tripRepository, TicketService ticketService, AiService aiService,TripService tripService) {
        this.tripRepository = tripRepository;
        this.ticketService = ticketService;
        this.aiService = aiService;
        this.tripService = tripService;
    }

    @Override
    public void run(String... args)  {
        logger.info("伺服器部屬完畢");
    }
}
