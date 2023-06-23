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

	/**
	 * 前台-票券訂單明細-獲得明細清單
	 */
	@GetMapping("/ticketorderdetails")
	public List<DetailDto> ticketOrderDetails(//
			@RequestParam final int memberId, @RequestParam final int orderId) {
		return orderDetailService.getItems(memberId, orderId);
	}

	/**
	 * 前台-票券訂單明細-退貨
	 */
	@PostMapping("/refundticketorderdetail")
	public boolean refundTicketOrderDetail(@RequestBody final DetailReqDto reqDto) {
		return orderDetailService.refundItem(reqDto);
	}

	/**
	 * 前台-票券訂單明細-評價
	 */
	@PostMapping("/ratingticketorderdetail")
	public boolean ratingTicketOrderDetail(@RequestBody final DetailReqDto reqDto) {
		return orderDetailService.ratingItem(reqDto);
	}

	// 定義請求物件
	@Data
	public static class DetailReqDto {
		private int memberId;
		private int orderId;
		private int ticketSnId;
		private String refundReason;
		private int rating;
		private String comment;
	}

	// 定義回傳物件
	@Data
	public static class DetailDto {
		private int ticketId;
		private int ticketSnId;
		private String serialNumber;
		private String name;
		private int price;
		private Date expiryDate;
		private int refundStatus;
	}
}
