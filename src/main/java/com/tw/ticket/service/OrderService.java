package com.tw.ticket.service;

public interface OrderService {

	// 綠界的支付介面設定
	public String ecpayCheckout();

	// 成立訂單
	public boolean makeOrder(int memberId, int couponId);
}
