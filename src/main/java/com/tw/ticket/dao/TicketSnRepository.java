package com.tw.ticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketSn;

@Repository
public interface TicketSnRepository extends JpaRepository<TicketSn, Long> {
	//
}
