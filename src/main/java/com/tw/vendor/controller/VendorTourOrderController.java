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
			result.add(dto);
		}

		return result;
	}

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

			result.add(dto);
		}
		return result;
	}

	@Data
	public static class TourOrderDto {
		private int tourGroupId;
		private String tripName;
		private Date startDate;
		private int confirmedTravelersNo;
		private int minTravelersNo;
		private int curTravelersNo;
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
	}
}
