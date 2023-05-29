package com.tw.ticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketImage;

import java.util.List;

@Repository
public interface TicketImageRepository extends JpaRepository<TicketImage, Long> {

    List<TicketImage> findByTicketId(Integer ticketId);

    //
}
