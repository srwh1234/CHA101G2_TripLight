package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.ticket.model.dao.TicketRepository;

@RestController
public class Hello {

	@Autowired
	private TicketRepository repository2;

	@Autowired
	private MemberRepository repository3;

	@Autowired
	private TicketOrderRepository ticketOrderRepository;

	@GetMapping("/test")
	public String hello() {

		// 測試取得
		final Member m = repository3.findById(1).get();
		final Ticket t = repository2.findById(1).get();

		// System.out.println(repository.findByKeyMemberAndKeyTicket(m, t).getQuantity());

		// 測試寫入

		// repository.save(new TicketCart(m, t, 158));

		// 測試查詢
		// System.out.println(repository.findByKeyMember(m).size());

		// 測試從指定使用者
		// 找出訂單
		// 找出訂單明細
		// 找出目標票券
		final Member member = repository3.findById(1).get();

		// 訂單列表
		ticketOrderRepository.findByMemberId(member.getMemberId()).forEach(order -> {
			System.out.println(order.getTicketOrderId());
		});

		// 找出訂單編號1號
		System.out.println(ticketOrderRepository.findById(1).get().getTicketOrderDetails().size());

		return "hello12";
	}
}
