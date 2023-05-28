package com.tw.ticket.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;
import com.tw.ticket.dao.TicketCartRepository;
import com.tw.ticket.dao.TicketOrderRepository;
import com.tw.ticket.dao.TicketSnRepository;
import com.tw.ticket.dao.TikcetRepository;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.TicketOrderDetail;
import com.tw.ticket.model.TicketSn;

@RestController
@RequestMapping("/TripLight")
public class Hello {

	@Autowired
	private TicketCartRepository repository;

	@Autowired
	private TikcetRepository repository2;

	@Autowired
	private MemberRepository repository3;

	@Autowired
	private TicketOrderRepository ticketOrderRepository;

	@Autowired
	private TicketSnRepository ticketSnRepository;

	@GetMapping("/test")
	public String hello() {

		// 測試取得
		final Member m = repository3.findById(1L).get();
		final Ticket t = repository2.findById(1L).get();

		System.out.println(repository.findByKeyMemberAndKeyTicket(m, t).getQuantity());

		// 測試寫入

		repository.save(new TicketCart(m, t, 58));

		// 測試查詢
		System.out.println(repository.findByKeyMember(m).size());

		// 測試從指定使用者
		// 找出訂單
		// 找出訂單明細
		// 找出目標票券
		final Member member = repository3.findById(1L).get();

		// 訂單列表
		ticketOrderRepository.findByMember(member).forEach(order -> {
			System.out.println(order.getTicketOrderId());
		});

		// 找出訂單編號1號
		System.out.println(ticketOrderRepository.findById(1L).get()
				.getTicketOrderDetails().size());

		// 測試寫入
		final TicketSn sn = ticketSnRepository.findById(9L).get();

		final TicketOrder ticketOrder = new TicketOrder();
		ticketOrder.setMember(member);
		ticketOrder.setPayType("TEST");
		ticketOrder.setPayDate(new Timestamp(System.currentTimeMillis()));
		ticketOrder.setTotalPrice(100);
		ticketOrder.setActualPrice(100);

		final TicketOrderDetail od = new TicketOrderDetail(ticketOrder, sn);
		od.setUnitPrice(100);
		od.setRefundStatus(0);

		ticketOrder.getTicketOrderDetails().add(od);

		ticketOrderRepository.save(ticketOrder);

		od.setRefundStatus(1);

		ticketOrderRepository.save(ticketOrder);

		return "hello12";
	}
}
