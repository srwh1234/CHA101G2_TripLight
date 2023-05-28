package com.tw.ticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.Ticket;

@Repository
public interface TikcetRepository extends JpaRepository<Ticket, Long> {
	//
}
