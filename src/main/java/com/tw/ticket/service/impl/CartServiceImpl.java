package com.tw.ticket.service.impl;

import static com.tw.ticket.controller.CartController.ADD_CART_ERROR;
import static com.tw.ticket.controller.CartController.ADD_CART_OK;
import static com.tw.ticket.controller.CartController.ADD_CART_SOLDOUT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;
import com.tw.ticket.controller.CartController.CartRequest;
import com.tw.ticket.controller.CartController.CartResponse;
import com.tw.ticket.controller.CartController.CartTicketResponse;
import com.tw.ticket.dao.TicketCartRepository;
import com.tw.ticket.dao.TicketRepository;
import com.tw.ticket.dao.TicketSnRepository;
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

	@Autowired
	private TicketSnRepository snRepository;

	// 票券購物車清單
	@Override
	public List<CartResponse> getMemberCarts(final int membeId) {

		final List<CartResponse> result = new ArrayList<>();

		ticketCartRepository.findByKeyMemberId(membeId).forEach(cart -> {
			final Ticket ticket = ticketRepository.findById(cart.getTicketId()).orElse(null);
			if (ticket != null) {
				final CartTicketResponse response = new CartTicketResponse(ticket);
				// 可用數量
				response.setAvailable(snRepository.countUsableSn(cart.getTicketId()));

				final CartResponse cartResponse = new CartResponse();
				cartResponse.setQuantity(cart.getQuantity());
				cartResponse.setTicket(response);
				result.add(cartResponse);
			}
		});

		return result;
	}

	// 放入購物車
	@Override
	public int addItem(final CartRequest cartRequest) {
		final int memberId = cartRequest.getMemberId();
		final int ticketId = cartRequest.getTicketId();
		final int quantity = cartRequest.getQuantity();

		// 確認數量
		if (quantity <= 0) {
			return ADD_CART_SOLDOUT;
		}

		final Member member = memberRepository.findById(memberId).orElse(null);
		final Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

		// 確認會員和票券
		if (member == null || ticket == null) {
			return ADD_CART_ERROR;
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
		return ADD_CART_OK;
	}
}
