package com.tw.ticket.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.MyUtils;
import com.tw.ticket.controller.CouponController.CouponResponse;
import com.tw.ticket.model.Coupon;
import com.tw.ticket.model.dao.CouponRepository;
import com.tw.ticket.model.dao.TicketOrderRepository;
import com.tw.ticket.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private TicketOrderRepository ticketOrderRepository;

	// 取得會員可用優惠券
	@Override
	public List<CouponResponse> getItems(final int memberId) {
		final List<CouponResponse> result = new ArrayList<>();

		for (final Coupon coupon : couponRepository.findAll()) {
			// 不在期間內
			if (!MyUtils.isContainNow(coupon.getStartDate(), coupon.getExpiryDate())) {
				continue;
			}
			// 用過了
			if (ticketOrderRepository.findByMemberIdAndCoupon(memberId, coupon) != null) {
				continue;
			}
			final CouponResponse response = new CouponResponse();
			response.setCouponId(coupon.getCouponId());
			response.setName(coupon.getName());
			response.setDiscount(coupon.getDiscount());
			result.add(response);
		}
		return result;
	}
}
