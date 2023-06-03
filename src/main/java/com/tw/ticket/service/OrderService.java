package com.tw.ticket.service;

import com.tw.ticket.controller.OrderController.OrderPageResponse;
import com.tw.ticket.controller.OrderController.OrderRequest;

public interface OrderService {

	// 訂單清單
	public OrderPageResponse getItems(OrderRequest orderRequest);

	// 綠界的支付介面設定
	public String ecpayCheckout();

	// 成立訂單
	public boolean makeOrder(int memberId, int couponId);
}
