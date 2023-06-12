package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.CouponController.CouponResponse;

public interface CouponService {

	// 取得會員可用優惠券
	public List<CouponResponse> getItems(int memberId);
}
