package com.tw.ticket.service;

import java.util.Map;

import com.tw.ticket.controller.OrderController.PageDto;
import com.tw.ticket.controller.OrderController.PageReqDto;

public interface OrderService {

	/**
	 * 獲得票券訂單清單分頁
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public PageDto getItem(PageReqDto reqDto);

	/**
	 * 綠界支付介面 設定後要丟給前端的資料(新增訂單)
	 *
	 * @param map 請求參數
	 * @return
	 */
	public String ecpayFromCarts(final Map<String, Object> map);

	/**
	 * 綠界支付介面 設定後要丟給前端的資料(現有訂單)
	 *
	 * @param map 請求參數
	 * @return
	 */
	public String ecpayFromOrder(final Map<String, Object> map);

	/**
	 * 綠界支付的結果 如果結帳成功就完成訂單
	 *
	 * @param memberId 會員編號
	 * @param orderId 訂單編號
	 * @param payType 付款類型
	 */
	public void ecpayConfirm(int memberId, int orderId, String payType);

	/**
	 * 測試用結帳
	 *
	 * @param memberId 會員編號
	 * @param couponId 優惠券編號
	 * @return
	 */
	public boolean testOrder(int memberId, int couponId);

}
