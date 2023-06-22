package com.tw.ticket.service.impl;

import static com.tw.ticket.model.TicketOrderDetail.REFUND_NONE;
import static com.tw.ticket.model.TicketSn.IN_USED;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.Config;
import com.tw.ticket.MyUtils;
import com.tw.ticket.controller.OrderController.OrderPageResponse;
import com.tw.ticket.controller.OrderController.OrderRequest;
import com.tw.ticket.controller.OrderController.OrderResponse;
import com.tw.ticket.model.Coupon;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.model.TicketOrder;
import com.tw.ticket.model.TicketOrderDetail;
import com.tw.ticket.model.TicketSn;
import com.tw.ticket.model.dao.CouponRepository;
import com.tw.ticket.model.dao.TicketCartRepository;
import com.tw.ticket.model.dao.TicketOrderDetailRepository;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.service.OrderService;
import com.tw.ticket.service.PromotionService;
import com.tw.ticket.thirdparty.ecpay.payment.integration.AllInOne;
import com.tw.ticket.thirdparty.ecpay.payment.integration.domain.AioCheckOutALL;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private Config config;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketCartRepository ticketCartRepository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private TicketOrderRepository ticketOrderRepository;

	@Autowired
	private TicketOrderDetailRepository ticketOrderDetailRepository;

	@Autowired
	private PromotionService promotionService;

	// 訂單清單
	@Override
	public OrderPageResponse getItems(final OrderRequest request) {
		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);

		final Page<TicketOrder> page = ticketOrderRepository.findByMemberId(//
				request.getMemberId(),	// 會員編號
				pageable				// 分頁
		);

		// 轉成自己定義的物件
		final OrderPageResponse pageResponse = new OrderPageResponse();
		pageResponse.setCurPage(request.getPage());
		pageResponse.setTotalPage(page.getTotalPages());

		page.getContent().forEach(order -> {
			int couponId = 0;
			String couponName = null;
			if (order.getCoupon() != null) {
				couponId = order.getCoupon().getCouponId();
				couponName = order.getCoupon().getName();
			}

			final OrderResponse response = new OrderResponse();
			response.setTicketOrderId(order.getTicketOrderId());
			response.setTicketCount(order.getTicketOrderDetails().size());
			response.setCouponId(couponId);
			response.setCouponName(couponName);
			response.setPayDate(order.getPayDate());
			response.setPayType(order.getPayType());
			response.setActualPrice(order.getActualPrice());
			pageResponse.getOrders().add(response);
		});

		return pageResponse;
	}

	// 建立訂單
	private TicketOrder makeOrder(final int memberId, final int couponId) {
		// 確認會員
		final Member member = memberRepository.findById(memberId).orElse(null);

		if (member == null) {
			return null;
		}

		// 總價與實際價格
		int totalPrice = 0;
		int actualPrice = 0;

		final HashMap<Integer, List<TicketSn>> temps = new HashMap<>();

		// 會員購物車
		final List<TicketCart> ticketCarts = ticketCartRepository.findByKeyMemberId(memberId);

		if (ticketCarts.size() <= 0) {
			return null;
		}

		for (final TicketCart cart : ticketCarts) {
			if (cart.getQuantity() <= 0) {
				continue;
			}
			final List<TicketSn> ticketSns = snRepository.searchUsableSn(cart.getTicketId());

			// 票券序號不足
			if (ticketSns.size() < cart.getQuantity()) {
				return null;
			}
			temps.put(cart.getTicketId(), ticketSns);

			// 計算總價
			final Ticket ticket = ticketSns.get(0).getTicket();

			// 促銷
			final int price = promotionService.getPrice(ticket);

			totalPrice += price * cart.getQuantity();
		}

		actualPrice = totalPrice;

		// 判斷是否使用了已經用過的優惠券
		final Coupon coupon = couponRepository.findById(couponId).orElse(null);
		if (coupon != null) {
			if (ticketOrderRepository.findByMemberIdAndCoupon(memberId, coupon) != null) {
				return null;
			}
			if (!MyUtils.isContainNow(coupon.getStartDate(), coupon.getExpiryDate())) {
				return null;
			}
			actualPrice = Math.max(1, actualPrice - coupon.getDiscount());
		}

		// 成立訂單
		final TicketOrder order = new TicketOrder();
		order.setMemberId(member.getMemberId());
		order.setCoupon(coupon);
		order.setPayDate(new Timestamp(System.currentTimeMillis()));
		order.setPayType("");
		order.setTotalPrice(totalPrice);
		order.setActualPrice(actualPrice);

		// 訂單明細的品項都要有獨一的序號
		final List<TicketOrderDetail> orderDetails = new ArrayList<>();
		for (final TicketCart cart : ticketCarts) {
			final List<TicketSn> ticketSns = temps.get(cart.getTicketId());

			// 按照購買數量 給予對應的序號數量
			for (int i = 0; i < cart.getQuantity(); i++) {
				final TicketSn ticketSn = ticketSns.get(i);
				ticketSn.setStatus(IN_USED);
				snRepository.save(ticketSn);

				// 訂單明細清單
				final TicketOrderDetail detail = new TicketOrderDetail(null, ticketSn);
				// 促銷
				final int price = promotionService.getPrice(ticketSn.getTicket());
				detail.setUnitPrice(price);
				detail.setRefundStatus(REFUND_NONE);
				orderDetails.add(detail);
			}
		}

		// 存檔
		ticketOrderRepository.save(order);

		// 更新訂單明細中的訂單編號
		orderDetails.forEach(detail -> {
			detail.getKey().setTicketOrderId(order.getTicketOrderId());
		});

		// 存檔訂單明細
		ticketOrderDetailRepository.saveAll(orderDetails);

		// 清空購物車
		ticketCartRepository.deleteByKeyMemberId(memberId);
		return order;
	}

	// 完成訂單
	private boolean confirmOrder(final int orderId, final String payType) {
		final TicketOrder order = ticketOrderRepository.findById(orderId).orElse(null);
		if (order == null) {
			return false;
		}
		order.setPayType(payType);
		// 存檔
		ticketOrderRepository.save(order);

		// 票券銷售量
		// 使用test按鈕時這邊的order.getTicketOrderDetails() 會是空的所以要自己讀
		final List<TicketOrderDetail> details = //
				ticketOrderDetailRepository.findByKeyTicketOrderId(orderId);

		final HashSet<Ticket> tickets = new HashSet<>();
		for (final TicketOrderDetail detail : details) {
			final Ticket ticket = detail.getKey().getTicketSn().getTicket();
			if (ticket != null) {
				final int totalSales = ticket.getTotalSales() + 1;
				ticket.setTotalSales(totalSales);
				tickets.add(ticket);
			}
		}
		// 更新票券的銷售量
		ticketRepository.saveAll(tickets);
		return true;
	}

	// 成立訂單(test)
	@Override
	public boolean testOrder(final int memberId, final int couponId) {
		final TicketOrder order = makeOrder(memberId, couponId);

		if (order == null) {
			return false;
		}
		confirmOrder(order.getTicketOrderId(), "TEST");
		return true;
	}

	// 綠界的支付結果
	@Override
	public void ecpayConfirm(final int memberId, final int orderId, final String payType) {
		final TicketOrder order = ticketOrderRepository.findById(orderId).orElse(null);
		if (order == null) {
			return;
		}
		confirmOrder(order.getTicketOrderId(), payType);
	}

	// XXX 綠界的支付介面設定(現有訂單)
	@Override
	public String ecpayCheckoutOrder(final Map<String, Object> map) {
		final int memberId = (int) map.get("memberId");
		final int orderId = (int) map.get("orderId");
		final String backUrl = (String) map.get("backUrl");
		final TicketOrder order = ticketOrderRepository.findById(orderId).orElse(null);
		if (order == null) {
			return null;
		}
		return getAllInOnePage(memberId, order, backUrl);
	}

	// 綠界的支付介面設定(新增訂單)
	@Override
	public String ecpayCheckoutMake(final Map<String, Object> map) {
		final int memberId = (int) map.get("memberId");
		final int couponId = (int) map.get("couponId");
		final String backUrl = (String) map.get("backUrl");
		// 建立訂單
		final TicketOrder order = makeOrder(memberId, couponId);

		if (order == null) {
			return null;
		}

		return getAllInOnePage(memberId, order, backUrl);
	}

	private String getAllInOnePage(final int memberId, final TicketOrder order, final String backUrl) {
		int couponId = 0;
		String itemName = "TripLight訂單";

		final Coupon coupon = order.getCoupon();
		if (coupon != null) {
			couponId = coupon.getCouponId();
			itemName += String.format(" (%s)", coupon.getName());
		}
		final String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

		final AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuId);
		obj.setMerchantTradeDate(MyUtils.getNowDateTimeString());
		obj.setTotalAmount(String.valueOf(order.getActualPrice()));
		obj.setTradeDesc("test Description");
		obj.setItemName("TripLight訂單###");
		obj.setReturnURL(config.getEcpayReturnUrl() + "/callback");
		obj.setClientBackURL(backUrl);

		// 參數傳遞
		obj.setCustomField1(String.valueOf(memberId));
		obj.setCustomField2(String.valueOf(couponId));
		obj.setCustomField3(String.valueOf(order.getTicketOrderId()));
		obj.setNeedExtraPaidInfo("Y");

		return new AllInOne("").aioCheckOut(obj, null);
	}
}
