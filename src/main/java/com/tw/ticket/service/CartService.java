package com.tw.ticket.service;

import com.tw.ticket.controller.CartController.CartRequest;

public interface CartService {

	// 放入購物車
	public int addItem(CartRequest cartRequest);
}
