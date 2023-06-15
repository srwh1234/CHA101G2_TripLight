package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.CouponController.CouponResponse;
import com.tw.ticket.model.Coupon;

public interface CouponService {

	// 取得會員可用優惠券
	public List<CouponResponse> getItems(int memberId);

	// 取得全部的優惠券
	public List<Coupon> getItems();

	// 取得指定的優惠券
	public Coupon getItem(int couponId);
}
