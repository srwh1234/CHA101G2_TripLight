package com.tw.ticket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.OrderService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 前台-票券訂單-訂單列表分頁
	 */
	@PostMapping("/ticketorders")
	public PageDto ticketOrders(@RequestBody final PageReqDto reqDto) {
		return orderService.getItem(reqDto);
	}

	/**
	 * 前台-購物車-快速結帳
	 */
	@GetMapping("/testCheckout")
	public boolean testCheckout(@RequestParam final int memberId, @RequestParam final int couponId) {
		return orderService.testOrder(memberId, couponId);
	}

	/**
	 * 前台-購物車-綠界結帳
	 * 安全碼 : 222
	 * 一般信用卡測試卡號 : 4311-9522-2222-2222
	 */
	@PostMapping("/ecpayCheckout")
	@ResponseBody
	public String ecpayCheckout(@RequestBody final Map<String, Object> map) {
		return orderService.ecpayFromCarts(map);
	}

	/**
	 * 前台-票券訂單-綠界結帳
	 * 安全碼 : 222
	 * 一般信用卡測試卡號 : 4311-9522-2222-2222
	 */
	@PostMapping("/ecpayCheckoutorder")
	@ResponseBody
	public String ecpayCheckoutOrder(@RequestBody final Map<String, Object> map) {
		return orderService.ecpayFromOrder(map);
	}

	/**
	 * 綠界回傳的結帳結果
	 * XXX 需要Https才收的到callback
	 * XXX map內的參數有很多
	 */
	@PostMapping("/callback")
	public ResponseEntity<String> handleCallback(@RequestParam final Map<String, String> map) {
		final int memberId = Integer.parseInt(map.get("CustomField1"));
		final int couponId = Integer.parseInt(map.get("CustomField2"));
		final int orderId = Integer.parseInt(map.get("CustomField3"));
		final int rtnCode = Integer.parseInt(map.get("RtnCode"));
		final String payType = map.get("PaymentType");

		// 結帳
		if (rtnCode == 1) {
			orderService.ecpayConfirm(memberId, orderId, payType);
		}
		return ResponseEntity.ok("OK");
	}

	// 定義請求物件
	@Data
	public static class PageReqDto {
		private int memberId;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class PageDto {
		private int curPage;
		private int totalPage;
		private ArrayList<OrderDto> orders = new ArrayList<>();
	}

	@Data
	public static class OrderDto {
		private int ticketOrderId;
		private int ticketCount;
		private int couponId;
		private String couponName;
		private Timestamp payDate;
		private String payType;
		private int actualPrice;
	}
}
