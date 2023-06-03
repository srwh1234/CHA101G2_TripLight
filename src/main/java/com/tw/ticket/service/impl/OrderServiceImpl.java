package com.tw.ticket.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;
import com.tw.ticket.dao.CouponRepository;
import com.tw.ticket.dao.TicketCartRepository;
import com.tw.ticket.dao.TicketOrderRepository;
import com.tw.ticket.dao.TicketSnRepository;
import com.tw.ticket.model.Coupon;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.TicketOrderDetail;
import com.tw.ticket.model.TicketSn;
import com.tw.ticket.service.OrderService;
import com.tw.ticket.thirdparty.ecpay.payment.integration.AllInOne;
import com.tw.ticket.thirdparty.ecpay.payment.integration.domain.AioCheckOutALL;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TicketCartRepository ticketCartRepository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private TicketOrderRepository ticketOrderRepository;

	// 成立訂單
	@Override
	public boolean makeOrder(final int memberId, final int couponId) {
		// 確認會員
		final Member member = memberRepository.findById(memberId).orElse(null);

		if (member == null) {
			return false;
		}

		// 總價與實際價格
		int totalPrice = 0;
		int actualPrice = 0;

		final HashMap<Integer, List<TicketSn>> temps = new HashMap<>();

		// 會員購物車
		final List<TicketCart> ticketCarts = ticketCartRepository.findByKeyMemberId(memberId);

		for (final TicketCart cart : ticketCarts) {
			final List<TicketSn> ticketSns = snRepository.searchUsableSn(cart.getTicketId());

			// 票券序號不足
			if (ticketSns.size() < cart.getQuantity()) {
				return false;
			}
			temps.put(cart.getTicketId(), ticketSns);

			// 計算總價
			final Ticket ticket = ticketSns.get(0).getTicket();
			totalPrice += ticket.getPrice() * cart.getQuantity();
		}

		actualPrice = totalPrice;

		// 判斷是否使用了已經用過的優惠券
		final Coupon coupon = couponRepository.findById(couponId).orElse(null);
		if (coupon != null) {
			if (ticketOrderRepository.findByMemberIdAndCoupon(memberId, coupon) != null) {
				return false;
			}
			actualPrice -= coupon.getDiscount();
		}

		// 成立訂單
		final TicketOrder order = new TicketOrder();
		order.setMemberId(member.getMemberId());
		order.setCoupon(coupon);
		order.setPayDate(new Timestamp(System.currentTimeMillis()));
		order.setPayType("TEST");
		order.setTotalPrice(totalPrice);
		order.setActualPrice(actualPrice);

		// 訂單明細的品項都要有獨一的序號
		for (final TicketCart cart : ticketCarts) {
			final List<TicketSn> ticketSns = temps.get(cart.getTicketId());

			// 按照購買數量 給予對應的序號數量
			for (int i = 0; i < cart.getQuantity(); i++) {
				final TicketSn ticketSn = ticketSns.get(i);
				ticketSn.setStatus(TicketSn.STATUS_IN_USE);
				snRepository.save(ticketSn);

				final TicketOrderDetail detail = new TicketOrderDetail(order, ticketSn);
				detail.setUnitPrice(ticketSn.getTicket().getPrice());
				detail.setRefundStatus(0);

				// 加到訂單明細清單
				order.getTicketOrderDetails().add(detail);
			}
		}

		// 存檔
		ticketOrderRepository.save(order);

		// 清空購物車
		ticketCartRepository.deleteByKeyMemberId(memberId);
		return true;
	}

	// 綠界的支付介面設定 (未完成)
	@Override
	public String ecpayCheckout() {
		final String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

		final AllInOne all = new AllInOne("");

		final AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuId);
		obj.setMerchantTradeDate("2023/06/03 08:05:23");
		obj.setTotalAmount("50");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setReturnURL("http://localhost:8080/callback");
		obj.setClientBackURL("http://localhost:8080/front-end/tickets_order.html");
		obj.setNeedExtraPaidInfo("Y");
		final String form = all.aioCheckOut(obj, null);
		return form;
	}

}
