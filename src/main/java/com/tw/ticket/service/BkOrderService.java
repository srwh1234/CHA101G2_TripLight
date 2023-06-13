package com.tw.ticket.service;

import com.tw.ticket.controller.BkOrderController.OrderRequest;
import com.tw.ticket.controller.BkOrderController.PageResponse;

public interface BkOrderService {

	// 回傳會員票券訂單
	public PageResponse getItems(OrderRequest request);
}
