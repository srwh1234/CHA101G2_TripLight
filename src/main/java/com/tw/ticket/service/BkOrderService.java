package com.tw.ticket.service;

import java.util.List;
import java.util.Map;

import com.tw.ticket.controller.BkOrderController.OrderDetailResponse;
import com.tw.ticket.controller.BkOrderController.OrderRequest;
import com.tw.ticket.controller.BkOrderController.PageResponse;

public interface BkOrderService {

	// 回傳會員票券訂單
	public PageResponse getItems(OrderRequest request);

	// 回傳會員票券訂單明細
	public List<OrderDetailResponse> getDetailItems(int orderId);

	// 變更票卷訂單明細退貨狀態
	public boolean updateItem(final Map<String, Object> map);
}
