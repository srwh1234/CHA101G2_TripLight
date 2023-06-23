package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.CouponController.CouponDto;
import com.tw.ticket.model.Coupon;

public interface CouponService {

	/**
	 * 取得會員可用優惠券
	 *
	 * @param memberId 會員編號
	 * @return
	 */
	public List<CouponDto> getItems(int memberId);

	/**
	 * 取得全部的優惠券
	 *
	 * @return
	 */
	public List<Coupon> getItems();

	/**
	 * 取得指定的優惠券
	 *
	 * @param couponId 優惠券編號
	 * @return
	 */
	public Coupon getItem(int couponId);

	/**
	 * 新增優惠券
	 *
	 * @param coupon 優惠券
	 * @return
	 */
	public boolean addItem(Coupon coupon);

	/**
	 * 編輯優惠券
	 *
	 * @param coupon 優惠券
	 * @return
	 */
	public boolean updateItem(Coupon coupon);
}
