package com.tw.ticket.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.MyUtils;
import com.tw.ticket.controller.CouponController.CouponDto;
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

	/**
	 * 取得會員可用優惠券
	 *
	 * @param memberId 會員編號
	 * @return
	 */
	@Override
	public List<CouponDto> getItems(final int memberId) {
		final List<CouponDto> result = new ArrayList<>();

		for (final Coupon coupon : couponRepository.findAll()) {
			// 不在期間內
			if (!MyUtils.isContainNow(coupon.getStartDate(), coupon.getExpiryDate())) {
				continue;
			}
			// 用過了
			if (ticketOrderRepository.findByMemberIdAndCoupon(memberId, coupon) != null) {
				continue;
			}
			final CouponDto dto = new CouponDto();
			dto.setCouponId(coupon.getCouponId());
			dto.setName(coupon.getName());
			dto.setDiscount(coupon.getDiscount());
			result.add(dto);
		}
		return result;
	}

	/**
	 * 取得全部的優惠券
	 *
	 * @return
	 */
	@Override
	public List<Coupon> getItems() {
		return couponRepository.findAll();
	}

	/**
	 * 取得指定的優惠券
	 *
	 * @param couponId 優惠券編號
	 * @return
	 */
	@Override
	public Coupon getItem(final int couponId) {
		return couponRepository.findById(couponId).orElse(null);
	}

	/**
	 * 新增優惠券
	 *
	 * @param coupon 優惠券
	 * @return
	 */
	@Override
	public boolean addItem(final Coupon coupon) {
		// 條件檢查
		if (!isValid(coupon)) {
			return false;
		}
		couponRepository.save(coupon);
		return true;
	}

	/**
	 * 編輯優惠券
	 *
	 * @param coupon 優惠券
	 * @return
	 */
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

	/**
	 * 檢查優惠券的資料是否正常
	 *
	 * @param coupon 優惠券
	 * @return
	 */
	private boolean isValid(final Coupon coupon) {
		// 名稱檢查
		if (MyUtils.isEmpty(coupon.getName())) {
			return false;
		}

		// 日期輸入錯誤
		if (coupon.getStartDate() == null || coupon.getExpiryDate() == null) {
			return false;
		}
		// 到期時間在開始時間之前
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
