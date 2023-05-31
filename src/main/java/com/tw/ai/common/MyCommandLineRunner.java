package com.tw.ai.common;




import com.tw.ticket.controller.ImageController;
import com.tw.ticket.dao.TicketRepository;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.TicketService;
import com.tw.trip.dao.TripDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripDAO tripDAO;

    private TicketService ticketService;



    public MyCommandLineRunner(TripDAO tripDAO, TicketService ticketService) {
        this.tripDAO = tripDAO;
        this.ticketService = ticketService;
    }

    @Override
    public void run(String... args)  {
        System.out.println("伺服器部屬完畢時執行");
        var taipei = ticketService.getTicket("台北");
        System.out.println(taipei.toString());
    }
}
