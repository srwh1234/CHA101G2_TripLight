package com.tw.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	// 定義回傳物件

	@Data
	public static class CouponResponse {
		private int couponId;
		private String name;
		private int discount;
	}
}
