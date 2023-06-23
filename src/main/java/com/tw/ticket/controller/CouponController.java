package com.tw.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.model.Coupon;
import com.tw.ticket.service.CouponService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class CouponController {

	@Autowired
	private CouponService couponService;

	/**
	 * 前台-購物車-取得會員可用優惠券清單
	 */
	@GetMapping("/coupons")
	public List<CouponDto> coupons(@RequestParam final int memberId) {
		return couponService.getItems(memberId);
	}

	/**
	 * 後台-票券資料管理-優惠券清單
	 */
	@GetMapping("/bk/coupons")
	public List<Coupon> coupons() {
		return couponService.getItems();
	}

	/**
	 * 後台-票券資料管理-要編輯的優惠券資料
	 */
	@GetMapping("/bk/coupon")
	public Coupon coupon(@RequestParam final int couponId) {
		return couponService.getItem(couponId);
	}

	/**
	 * 後台-票券資料管理-編輯優惠券
	 */
	@PostMapping("/bk/editcoupon")
	public boolean editCoupon(@RequestBody final Coupon coupon) {
		return couponService.updateItem(coupon);
	}

	/**
	 * 後台-票券資料管理-新增優惠券
	 */
	@PostMapping("/bk/addcoupon")
	public boolean addCoupon(@RequestBody final Coupon coupon) {
		return couponService.addItem(coupon);
	}

	// 定義回傳物件
	@Data
	public static class CouponDto {
		private int couponId;
		private String name;
		private int discount;
	}
}
