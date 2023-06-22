package com.tw.ticket.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	/**
	 * 後台-票券促銷管理-訂單列表查詢
	 */
	@PostMapping("/orders")
	public PageDto orders(@RequestBody final PageReqDto request) {
		return bkOrderService.getItems(request);
	}

	/**
	 * 後台-票券促銷管理-訂單明細查詢
	 */
	@GetMapping("/orderdetail")
	public List<DetailDto> orderdetail(	//
			@RequestParam final int employeeId, @RequestParam final int orderId) {
		return bkOrderService.getDetailItems(orderId);
	}

	/**
	 * 後台-票券促銷管理-退貨審核
	 */
	@PostMapping("/refund")
	public boolean refund(@RequestBody final Map<String, Object> map) {
		return bkOrderService.updateItem(map);
	}

	// 定義請求物件
	@Data
	public static class PageReqDto {
		private String keyword;
		private boolean refundChecked;
		private int employeeId;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class PageDto {
		private int curPage;
		private int totalPage;
		ArrayList<OrderDto> orders = new ArrayList<>();
	}

	@Data
	public static class OrderDto {
		private int ticketOrderId;
		private int memberId;
		private String memberName;
		private int ticketCount;
		private Timestamp payDate;
		private String payType;
		private int actualPrice;
	}

	@Data
	public static class DetailDto {
		private int ticketSnId;
		private String name;
		private int price;
		private Date expiryDate;
		private int refundStatus;
		private String refundReason;
	}

}
