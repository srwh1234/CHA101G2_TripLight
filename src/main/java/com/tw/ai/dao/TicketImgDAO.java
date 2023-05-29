package com.tw.ai.dao;




import com.tw.ticket.model.TicketImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketImgDAO  extends JpaRepository<TicketImage, Integer> {
    List<TicketImage> findByTicketId(int ticketId);
}
