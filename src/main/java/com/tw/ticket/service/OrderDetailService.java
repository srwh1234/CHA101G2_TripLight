package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.OrderDetailController.DetailDto;
import com.tw.ticket.controller.OrderDetailController.DetailReqDto;

public interface OrderDetailService {

	/**
	 * 取得訂單明細清單
	 *
	 * @param memberId 會員編號
	 * @param orderId 訂單編號
	 * @return
	 */
	public List<DetailDto> getItems(int memberId, int orderId);

	/**
	 * 退貨申請
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public boolean refundItem(DetailReqDto reqDto);

	/**
	 * 評價
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public boolean ratingItem(DetailReqDto reqDto);
}
