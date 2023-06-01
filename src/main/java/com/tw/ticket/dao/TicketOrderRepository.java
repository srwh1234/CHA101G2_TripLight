package com.tw.ticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.member.model.Member;
import com.tw.ticket.model.TicketOrder;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {

	// 找出同一個Member的TicketOrder
	public List<TicketOrder> findByMember(Member member);
}
