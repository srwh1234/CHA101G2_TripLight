package com.tw.ticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketFavorite;

@Repository
public interface TicketFavoriteRepository extends JpaRepository<TicketFavorite, Long> {
	//
}
