package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.CartService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class CartController {

	@Autowired
	private CartService cartService;

	// 票券購物車車
	@PostMapping("/ticketcart")
	public boolean comments(@RequestBody final CartRequest cartRequest) {
		return cartService.addItem(cartRequest);
	}

	// 定義請求物件
	@Data
	public static class CartRequest {
		private long memberId;
		private long ticketId;
		private int quantity;
	}
}
