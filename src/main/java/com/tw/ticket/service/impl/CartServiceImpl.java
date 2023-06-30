package com.tw.ticket.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.CartController.AddReqDto;
import com.tw.ticket.controller.CartController.CartDto;
import com.tw.ticket.controller.CartController.DescTicketDto;
import com.tw.ticket.controller.CartController.QuantityReqDto;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.model.TicketCart.PrimaryKey;
import com.tw.ticket.model.dao.TicketCartRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.redis.TicketCartsRedis;
import com.tw.ticket.service.CartService;
import com.tw.ticket.service.PromotionService;

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

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private TicketCartsRedis ticketCartsRedis;

	/**
	 * 票券購物車清單
	 *
	 * @param membeId 會員編號
	 * @param sessionId 給非會員使用
	 * @return
	 */
	@Override
	public List<CartDto> getItems(final int memberId, final String sessionId) {

		if (memberId == 0) {
			return ticketCartsRedis.getItems(sessionId);
		}
		ticketCartsRedis.convertItems(memberId, sessionId);

		final List<CartDto> result = new ArrayList<>();

		for (final TicketCart cart : ticketCartRepository.findByKeyMemberId(memberId)) {
			// 這邊好像要查詢很多次...
			final Ticket ticket = ticketRepository.findById(cart.getTicketId()).orElse(null);

			if (ticket == null) {
				continue;
			}
			final DescTicketDto descDto = new DescTicketDto(ticket);
			// 可用數量
			final int available = snRepository.countUsableSn(cart.getTicketId());
			descDto.setAvailable(available);

			// 促銷
			descDto.setPromotion(promotionService.getItem(cart.getTicketId()));

			// 圖片
			descDto.setImage(imageService.findImgUrl(ticket.getTicketId()));

			if (cart.getQuantity() > available) {
				cart.setQuantity(available);
				ticketCartRepository.save(cart);
			}

			final CartDto cartDto = new CartDto();
			cartDto.setQuantity(cart.getQuantity());
			cartDto.setTicket(descDto);

			result.add(cartDto);
		}
		return result;
	}

	/**
	 * 放入購物車
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	@Override
	public int addItem(final AddReqDto reqDto) {
		final int memberId = reqDto.getMemberId();
		final int ticketId = reqDto.getTicketId();
		final int quantity = reqDto.getQuantity();

		// 確認數量
		if (quantity <= 0) {
			return ADD_CART_ERROR;
		}

		final Member member = memberRepository.findById(memberId).orElse(null);
		final Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

		// 確認票券
		if (ticket == null) {
			return ADD_CART_ERROR;
		}
		// 下架中
		if (ticket.getStatus() == Ticket.DISABLED) {
			return ADD_CART_DISABLE;
		}

		// 確認票券數量
		if (quantity > snRepository.countUsableSn(ticketId)) {
			return ADD_CART_SOLDOUT;
		}

		// 確認會員
		if (member == null) {
			return ticketCartsRedis.addItem(reqDto);
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

	/**
	 * 變更數量
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	@Override
	public boolean updateItem(final QuantityReqDto reqDto) {
		final int memberId = reqDto.getMemberId();
		final int ticketId = reqDto.getTicketId();
		final int modify = reqDto.getModify();

		if (memberId == 0) {
			return ticketCartsRedis.updateItem(reqDto);
		}
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

	/**
	 * 移除購物車品項
	 *
	 * @param memberId 會員編號
	 * @param ticketId 票券編號
	 * @param sessionId 給非會員使用
	 * @return
	 */
	@Override
	public boolean removeItem(final int memberId, final int ticketId, final String sessionId) {
		if (memberId == 0) {
			ticketCartsRedis.removeItem(sessionId, ticketId);
		} else {
			ticketCartRepository.deleteById(new PrimaryKey(memberId, ticketId));
		}
		return true;
	}
}
