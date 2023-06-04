package com.tw.ticket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.OrderService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	// 訂單清單
	@PostMapping("/ticketorders")
	public OrderPageResponse ticketOrders(@RequestBody final OrderRequest request) {
		return orderService.getItems(request);
	}

	/**
	 * 安全碼 : 222
	 * 一般信用卡測試卡號 : 4311-9522-2222-2222
	 */
	@GetMapping("/ecpayCheckout")
	public String ecpayCheckout() {
		return orderService.ecpayCheckout();
	}

	@GetMapping("/testCheckout")
	public boolean testCheckout(//
			@RequestParam("memberId") final int memberId,//
			@RequestParam("couponId") final int couponId) {
		return orderService.makeOrder(memberId, couponId);
	}

	// 定義請求物件
	@Data
	public static class OrderRequest {
		private int memberId;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class OrderPageResponse {
		private int curPage;
		private int totalPage;
		private ArrayList<OrderResponse> orders = new ArrayList<>();
	}

	@Data
	public static class OrderResponse {
		private int ticketOrderId;
		private int ticketCount;
		private String couponName;
		private Timestamp payDate;
		private String payType;
		private int actualPrice;
	}
}
