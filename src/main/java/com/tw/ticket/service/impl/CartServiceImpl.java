package com.tw.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;
import com.tw.ticket.controller.CartController.CartRequest;
import com.tw.ticket.dao.TicketCartRepository;
import com.tw.ticket.dao.TicketRepository;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketCartRepository ticketCartRepository;

	// 放入購物車
	@Override
	public boolean addItem(final CartRequest cartRequest) {
		final long memberId = cartRequest.getMemberId();
		final long ticketId = cartRequest.getTicketId();
		final int quantity = cartRequest.getQuantity();

		// 確認數量
		if (quantity <= 0) {
			return false;
		}

		final Member member = memberRepository.findById(memberId).orElse(null);
		final Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

		// 確認會員和票券
		if (member == null || ticket == null) {
			return false;
		}

		// 確認票券數量

		// 確認購物車
		TicketCart ticketCart = ticketCartRepository.findByKeyMemberIdAndKeyTicketId(memberId, ticketId);

		// 如果有
		if (ticketCart != null) {
			ticketCart.addQuantity(quantity);
		} else {
			ticketCart = new TicketCart(memberId, ticketId, quantity);
		}

		ticketCartRepository.save(ticketCart);
		return true;
	}
}
