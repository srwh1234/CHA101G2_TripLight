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

	@GetMapping("/coupons")
	public List<CouponResponse> coupons(@RequestParam final int memberId) {
		return couponService.getItems(memberId);
	}

	@GetMapping("/bk/coupons")
	public List<Coupon> coupons() {
		return couponService.getItems();
	}

	@GetMapping("/bk/coupon")
	public Coupon coupon(@RequestParam final int couponId) {
		return couponService.getItem(couponId);
	}

	@PostMapping("/bk/addcoupon")
	public boolean addCoupon(@RequestBody final Coupon coupon) {
		return couponService.addItem(coupon);
	}

	@PostMapping("/bk/editcoupon")
	public boolean editCoupon(@RequestBody final Coupon coupon) {
		return couponService.updateItem(coupon);
	}

	// 定義回傳物件

	@Data
	public static class CouponResponse {
		private int couponId;
		private String name;
		private int discount;
	}
}
