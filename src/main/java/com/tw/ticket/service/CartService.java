package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.CartController.AddReqDto;
import com.tw.ticket.controller.CartController.CartDto;
import com.tw.ticket.controller.CartController.QuantityReqDto;

public interface CartService {

	public static int ADD_CART_OK = 1; // 添加成功
	public static int ADD_CART_SOLDOUT = 2;// 此商品數量不足
	public static int ADD_CART_DISABLE = 3;// 此商品已下架
	public static int ADD_CART_ERROR = 4;// 發生未知的錯誤

	/**
	 * 票券購物車清單
	 *
	 * @param memberId 會員編號
	 * @param sessionId 給非會員使用
	 * @return
	 */
	public List<CartDto> getItems(int memberId, String sessionId);

	/**
	 * 放入購物車
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public int addItem(AddReqDto reqDto);

	/**
	 * 變更數量
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public boolean updateItem(QuantityReqDto reqDto);

	/**
	 * 移除購物車品項
	 *
	 * @param memberId 會員編號
	 * @param ticketId 票券編號
	 * @param sessionId 給非會員使用
	 * @return
	 */
	public boolean removeItem(int memberId, int ticketId, String sessionId);
}
