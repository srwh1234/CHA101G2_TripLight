package com.tw.ticket.service.impl;

import static com.tw.ticket.controller.CartController.ADD_CART_DISABLE;
import static com.tw.ticket.controller.CartController.ADD_CART_ERROR;
import static com.tw.ticket.controller.CartController.ADD_CART_OK;
import static com.tw.ticket.controller.CartController.ADD_CART_SOLDOUT;
import static com.tw.ticket.model.Ticket.DISABLED;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.CartController.CartRequest;
import com.tw.ticket.controller.CartController.CartResponse;
import com.tw.ticket.controller.CartController.CartTicketResponse;
import com.tw.ticket.controller.CartController.ModifyRequest;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.model.TicketCart.PrimaryKey;
import com.tw.ticket.model.dao.TicketCartRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
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

	@Autowired
	private ImageServiceImpl imageService;

	// 票券購物車清單
	@Override
	public List<CartResponse> getItems(final int membeId) {

		final List<CartResponse> result = new ArrayList<>();

		ticketCartRepository.findByKeyMemberId(membeId).forEach(cart -> {

			// XXX 這邊好像要查詢很多次...
			final Ticket ticket = ticketRepository.findById(cart.getTicketId()).orElse(null);

			if (ticket != null) {
				final CartTicketResponse cartTicketResponse = new CartTicketResponse(ticket);
				// 可用數量
				final int available = snRepository.countUsableSn(cart.getTicketId());
				cartTicketResponse.setAvailable(available);

				// 圖片
				final String image = imageService.findImgUrl(ticket.getTicketId());
				cartTicketResponse.setImage(image);

				if (cart.getQuantity() > available) {
					cart.setQuantity(available);
					ticketCartRepository.save(cart);
				}

				final CartResponse cartResponse = new CartResponse();
				cartResponse.setQuantity(cart.getQuantity());
				cartResponse.setTicket(cartTicketResponse);

				result.add(cartResponse);
			}
		});
		return result;
	}

	// 放入購物車
	@Override
	public int addItem(final CartRequest request) {
		final int memberId = request.getMemberId();
		final int ticketId = request.getTicketId();
		final int quantity = request.getQuantity();

		// 確認數量
		if (quantity <= 0) {
			return ADD_CART_ERROR;
		}

		final Member member = memberRepository.findById(memberId).orElse(null);
		final Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

		// 確認會員和票券
		if (member == null || ticket == null) {
			return ADD_CART_ERROR;
		}

		// 下架中
		if (ticket.getStatus() == DISABLED) {
			return ADD_CART_DISABLE;
		}

		// 確認票券數量
		if (quantity > snRepository.countUsableSn(ticketId)) {
			return ADD_CART_SOLDOUT;
		}

		// 購物車物件
		TicketCart ticketCart = ticketCartRepository.findByKeyMemberIdAndKeyTicketId(memberId, ticketId);

		// 如果有就只是增加數量
		if (ticketCart != null) {
			ticketCart.addQuantity(quantity);
		} else {
			ticketCart = new TicketCart(memberId, ticketId, quantity);
		}

		ticketCartRepository.save(ticketCart);
		return ADD_CART_OK;
	}

	// 變更購物車數量
	@Override
	public boolean updateItem(final ModifyRequest request) {
		final int memberId = request.getMemberId();
		final int ticketId = request.getTicketId();
		final int modify = request.getModify();

		// 購物車物件
		final TicketCart ticketCart = //
				ticketCartRepository.findByKeyMemberIdAndKeyTicketId(memberId, ticketId);

		if (ticketCart == null) {
			return false;
		}
		final int available = snRepository.countUsableSn(ticketId);
		int quantity = ticketCart.getQuantity() + modify;

		if (quantity < 0) {
			return false;
		}
		boolean isValid = true;

		// 超過可獲得數量
		if (quantity > available) {
			quantity = available;
			isValid = false;
		}
		ticketCart.setQuantity(quantity);
		ticketCartRepository.save(ticketCart);
		return isValid;
	}

	// 移除購物車物件
	@Override
	public boolean removeItem(final int memberId, final int ticketId) {
		ticketCartRepository.deleteById(new PrimaryKey(memberId, ticketId));
		return true;
	}
}
