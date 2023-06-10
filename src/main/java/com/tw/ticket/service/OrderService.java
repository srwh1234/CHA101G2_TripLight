package com.tw.ticket.service;

import java.util.Map;

import com.tw.ticket.controller.OrderController.OrderPageResponse;
import com.tw.ticket.controller.OrderController.OrderRequest;

public interface OrderService {

	// 訂單清單
	public OrderPageResponse getItems(OrderRequest request);

	// 綠界的支付介面設定(新增訂單)
	public String ecpayCheckoutMake(final Map<String, Object> map);

	// 綠界的支付介面設定(現有訂單)
	public String ecpayCheckoutOrder(final Map<String, Object> map);

	// 綠界的支付結果
	public void ecpayConfirm(int memberId, int orderId, String payType);

	// test
	public boolean testOrder(int memberId, int couponId);

}
