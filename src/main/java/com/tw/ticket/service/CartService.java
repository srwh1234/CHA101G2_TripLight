package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.CartController.CartRequest;
import com.tw.ticket.controller.CartController.CartResponse;
import com.tw.ticket.controller.CartController.ModifyRequest;

public interface CartService {

	// 票券購物車清單
	public List<CartResponse> getItems(int membeId);

	// 放入購物車
	public int addItem(CartRequest request);

	// 變更數量
	public boolean updateItem(ModifyRequest request);

	// 移除購物車物件
	public boolean removeItem(int memberId, int ticketId);
}
