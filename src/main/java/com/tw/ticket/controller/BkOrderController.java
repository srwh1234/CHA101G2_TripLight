package com.tw.ticket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.BkOrderService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bk")
public class BkOrderController {

	@Autowired
	private BkOrderService bkOrderService;

	@PostMapping("/orders")
	public PageResponse orders(@RequestBody final OrderRequest request) {
		return bkOrderService.getItems(request);
	}

	@GetMapping("/orderdetail")
	public int orderdetail(@RequestParam final int employeeId, @RequestParam final int orderId) {
		System.out.println(orderId);
		return 0;
	}

	// 定義請求物件
	@Data
	public static class OrderRequest {
		private String keyword;
		private int employeeId;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class PageResponse {
		private int curPage;
		private int totalPage;
		ArrayList<OrderResponse> orders = new ArrayList<>();
	}

	@Data
	public static class OrderResponse {
		private int ticketOrderId;
		private int memberId;
		private String memberName;
		private int ticketCount;
		private Timestamp payDate;
		private String payType;
		private int actualPrice;
	}
}
