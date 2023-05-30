package com.tw.ai.service;


import com.google.gson.Gson;
import com.tw.ai.common.GetMethod;
import com.tw.ticket.dao.TicketImageRepository;
import com.tw.ticket.dao.TicketRepository;
import com.tw.ticket.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements GetMethod {
    private TicketRepository ticketRepository;
    private TicketImageRepository ticketImageRepository;


    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketImageRepository ticketImageRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketImageRepository = ticketImageRepository;
    }

    public List<Ticket> getTicket(String destination){
        List<Ticket> result = ticketRepository.findByCityContaining(destination);
        for(var ticket:result){
            var ticketImg = ticketImageRepository.findByTicketId(1);
            byte[] image = ticketImg.get(0).getImage();  // 獲得圖片byte[]
            var url = convertBlobToUrl(image);
            // 將圖片byte轉成url
            ticket.setImgUrl(url);
            // 將圖片byte轉成url
        }
        return result;
    }
}
