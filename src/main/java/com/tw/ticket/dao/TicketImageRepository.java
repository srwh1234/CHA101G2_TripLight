package com.tw.ticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketImage;

@Repository
public interface TicketImageRepository extends JpaRepository<TicketImage, Long> {

	//
	public List<TicketImage> findByTicketId(long ticketId);

}
