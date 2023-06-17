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

	// 取得全部的優惠券
	@Override
	public List<Coupon> getItems() {
		return couponRepository.findAll();
	}

	// 取得指定的優惠券
	@Override
	public Coupon getItem(final int couponId) {
		return couponRepository.findById(couponId).orElse(null);
	}

	// 新增優惠券
	@Override
	public boolean addItem(final Coupon coupon) {
		// 條件檢查
		if (!isValid(coupon)) {
			return false;
		}
		couponRepository.save(coupon);
		return true;
	}

	// 編輯優惠券
	@Override
	public boolean updateItem(final Coupon coupon) {
		// 不存在的資料
		if (!couponRepository.existsById(coupon.getCouponId())) {
			return false;
		}
		// 條件檢查
		if (!isValid(coupon)) {
			return false;
		}
		couponRepository.save(coupon);
		return true;
	}

	// 檢查優惠券的資料是否正常
	private boolean isValid(final Coupon coupon) {
		// 名稱檢查
		if (MyUtils.isEmpty(coupon.getName())) {
			return false;
		}

		// 日期輸入錯誤
		if (coupon.getStartDate() == null || coupon.getExpiryDate() == null) {
			return false;
		}
		// 到期時間在
		if (coupon.getExpiryDate().before(coupon.getStartDate())) {
			return false;
		}

		// 優惠券到期日已經過期
		if (MyUtils.isBeforeNow(coupon.getExpiryDate())) {
			return false;
		}
		// 折扣金額不可小於1
		if (coupon.getDiscount() <= 0) {
			return false;
		}
		return true;
	}
}
