package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.CartController.CartRequest;
import com.tw.ticket.controller.CartController.CartResponse;

public interface CartService {

	// 票券購物車清單
	public List<CartResponse> getMemberCarts(int membeId);

	// 放入購物車
	public int addItem(CartRequest cartRequest);
}
