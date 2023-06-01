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

	public static int ADD_CART_OK = 1; // 添加成功
	public static int ADD_CART_SOLDOUT = 2;// 此商品已完售
	public static int ADD_CART_ERROR = 3;// 發生未知的錯誤

	@Autowired
	private CartService cartService;

	// 票券購物車車
	@PostMapping("/ticketcart")
	public int comments(@RequestBody final CartRequest cartRequest) {
		return cartService.addItem(cartRequest);
	}

	// 定義請求物件
	@Data
	public static class CartRequest {
		private int memberId;
		private int ticketId;
		private int quantity;
	}
}
