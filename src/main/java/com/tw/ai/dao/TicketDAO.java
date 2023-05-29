package com.tw.ai.dao;





import com.tw.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketDAO extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByCityContaining(String city);

}
