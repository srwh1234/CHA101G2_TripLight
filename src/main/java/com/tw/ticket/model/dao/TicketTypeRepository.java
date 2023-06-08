package com.tw.ticket.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.ticket.model.TicketType;

public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {

	// 找出指定名稱的票券類型
	public TicketType findByName(String name);
}
