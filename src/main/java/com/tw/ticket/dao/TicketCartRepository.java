package com.tw.ticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.member.model.Member;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.model.TicketCart.PrimaryKey;

@Repository
public interface TicketCartRepository extends JpaRepository<TicketCart, PrimaryKey> {

	// 不須實作 JpaRepository會自動解析 (其中 findBy And 是關鍵字)
	// KeyMember 表示 是變數key內的 member
	// KeyTicket 表示 是變數key內的 ticket
	public TicketCart findByKeyMemberAndKeyTicket(Member member, Ticket ticket);

	// 找出同一個Member的TicketCart
	public List<TicketCart> findByKeyMember(Member member);
}
