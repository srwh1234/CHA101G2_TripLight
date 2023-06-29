package com.tw.vendor.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.thirdparty.mail.MailService;
import com.tw.trip.pojo.TripOrder;
import com.tw.vendor.dao.TourGroupDetailRepository2;
import com.tw.vendor.dao.TourGroupRepository2;
import com.tw.vendor.dao.TripOrderRepository2;
import com.tw.vendor.dao.TripRepository2;
import com.tw.vendor.model.TourGroup2;
import com.tw.vendor.model.Trip2;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vd")
public class VendorTourOrderController {

	@Autowired
	private TourGroupRepository2 tourGroupRepository;

	@Autowired
	private TourGroupDetailRepository2 tourGroupDetailRepository;

	@Autowired
	private TripRepository2 tripRepository;

	@Autowired
	private TripOrderRepository2 tripOrderRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MailService mailService;

	// 旅遊團列表
	@GetMapping("/tourOrders")
	public List<TourOrderDto> tourOrders() {
		final List<TourOrderDto> result = new ArrayList<>();
		for (final TourGroup2 tg : tourGroupRepository.findAll()) {
			final Trip2 trip = tripRepository.findById(tg.getTripId()).orElse(null);

			if (trip == null) {
				continue;
			}
			final TourOrderDto dto = new TourOrderDto();
			dto.setTourGroupId(tg.getTourGroupId());
			dto.setTripName(trip.getTripName());
			dto.setStartDate(tg.getStartDate());
			dto.setConfirmedTravelersNo(tg.getConfirmedTravelersNo());
			dto.setMinTravelersNo(tg.getMinTravelersNo());
			dto.setCurTravelersNo(tourGroupDetailRepository.findByTourGroupId(tg.getTourGroupId()).size());

			// 判斷是否決定成團/不成團
			final List<TripOrder> orders = tripOrderRepository.findByTourGroupId(tg.getTourGroupId());
			if (!orders.isEmpty()) {
				dto.setStatus(orders.get(0).getOrderStatus());
			}
			result.add(dto);
		}
		return result;
	}

	// 旅遊團訂單
	@GetMapping("/order/{id}")
	public List<OrderDetailDto> order(@PathVariable final int id) {
		final List<OrderDetailDto> result = new ArrayList<>();
		for (final TripOrder order : tripOrderRepository.findByTourGroupId(id)) {

			final Member member = memberRepository.findById(order.getMemberId()).orElse(null);
			if (member == null) {
				continue;
			}
			final OrderDetailDto dto = new OrderDetailDto();
			dto.setTripOrderId(order.getTripOrderId());
			dto.setMemberName(member.getMemberNameLast() + member.getMemberNameFirst());
			dto.setOrderStatus(order.getOrderStatus());
			dto.setPayDate(order.getPayDate());
			dto.setPayType(order.getPayType());
			dto.setTotalPrice(order.getTotalPrice());
			dto.setTravelersAdult(order.getTravelersAdult());
			dto.setTravelersChildren(order.getTravelersChildren());
			dto.setPaymentStatus(order.getPaymentStatus());
			dto.setRefundStatus(order.getRefundStatus());

			result.add(dto);
		}
		return result;
	}

	// 確定出團/不出團
	@GetMapping("/orderCheck/{id:[0-9]+}/{status:[0-9]+}")
	public boolean orderCheck(@PathVariable final int id, @PathVariable final int status) {
		final Trip2 trip = tripRepository.findById(id).orElse(null);

		if (trip != null) {
			final List<TripOrder> orders = tripOrderRepository.findByTourGroupId(id);
			for (final TripOrder order : orders) {
				final Member member = memberRepository.findById(order.getMemberId()).orElse(null);
				if (member == null) {
					continue;
				}
				order.setOrderStatus(status);

				if (status == 0) {
					sendRefundOkMail(member, order, trip, true);
				} else {
					sendRefundOkMail(member, order, trip, false);
				}

			}
			tripOrderRepository.saveAll(orders);
		}

		return true;
	}

	private void sendRefundOkMail(final Member member, final TripOrder order, final Trip2 trip, final boolean isOk) {
		final String to = member.getMemberEmail();

		// XXX 測試用 請不要寄給我
		// if (member.getMemberId() == 1) {
		// to = "srwh3577@gmail.com";
		// }

		final String subject = "TripLight旅遊團通知";

		final String html = """
				<html><head><style>
				        table,tr,th,td {
				            border: 1px solid #09777d;
				            border-collapse: collapse;
				            padding: 2rem;
				            font-size: 1.5rem;
				        }
				        table { margin-top: 2rem; }
				        th {color: lavender;background-color: #26bec9; }
				        td {color: deeppink; min-width: 25rem; }
				    </style></head>
				<body><table>
				            <tr><th>編號</th><td>%s</td></tr>
				            <tr><th>商品名稱</th><td>%s</td></tr>
				            <tr><th>金額</th><td>%d</td></tr>
				            <tr><th>出團結果</th><td>%s</td></tr>
				        </table></body></html>""";

		// XXX 要改網域
		String result = "人數不足無法出團";

		if (isOk) {
			result = """
					<p>出團請結帳</p>
					<a href="http://localhost:8080/front-end/group_orderpayment.html">
					前往結帳
					</a>
					""";
		}

		final String text = String.format(html,				//
				order.getTourGroupId(),						//
				trip.getTripName(),							//
				order.getTotalPrice(),						//
				result										//
		);

		mailService.sendHtmlEmail(to, subject, text);
	}

	@Data
	public static class TourOrderDto {
		private int tourGroupId;
		private String tripName;
		private Date startDate;
		private int confirmedTravelersNo;
		private int minTravelersNo;
		private int curTravelersNo;
		private int status;
	}

	@Data
	public static class OrderDetailDto {
		private int tripOrderId;
		private String memberName;
		private int orderStatus;
		private Timestamp payDate;
		private String payType;
		private int totalPrice;
		private int travelersAdult;
		private int travelersChildren;
		private int paymentStatus;
		private int refundStatus;
	}
}
