package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 安全碼 : 222
	 * 一般信用卡測試卡號 : 4311-9522-2222-2222
	 */
	@GetMapping("/ecpayCheckout")
	public String ecpayCheckout() {
		return orderService.ecpayCheckout();
	}

}
