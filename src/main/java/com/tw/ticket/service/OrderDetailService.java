package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.OrderDetailController.DetailRequest;
import com.tw.ticket.controller.OrderDetailController.DetailResponse;

public interface OrderDetailService {

	// 取得訂單明細清單
	public List<DetailResponse> getItems(int memberId, int orderId);

	// 退貨申請
	public boolean refundItem(DetailRequest request);

	// 評價
	public boolean ratingItem(DetailRequest request);
}
