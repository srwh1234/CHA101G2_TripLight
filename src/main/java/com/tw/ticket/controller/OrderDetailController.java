package com.tw.ticket.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.OrderDetailService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	// 訂單明細清單
	@GetMapping("/ticketorderdetails")
	public List<DetailResponse> ticketOrderDetails(			//
			@RequestParam("memberId") final int memberId,	//
			@RequestParam("orderId") final int orderId) {

		return orderDetailService.getItems(memberId, orderId);
	}

	// 訂單明細中退貨
	@PostMapping("/refundticketorderdetail")
	public boolean refundTicketOrderDetail(@RequestBody final DetailRequest request) {
		return orderDetailService.refundItem(request);
	}

	// 訂單明細中評價
	@PostMapping("/ratingticketorderdetail")
	public boolean ratingTicketOrderDetail(@RequestBody final DetailRequest request) {
		return orderDetailService.ratingItem(request);
	}

	// 定義請求物件
	@Data
	public static class DetailRequest {
		private int memberId;
		private int orderId;
		private int ticketSnId;
		private String refundReason;
		private int rating;
		private String comment;
	}

	// 定義回傳物件
	@Data
	public static class DetailResponse {
		private int ticketId;
		private int ticketSnId;
		private String serialNumber;
		private String name;
		private int price;
		private Date expiryDate;
		private int refundStatus;
	}
}
