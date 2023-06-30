package com.tw.ticket.redis;

import java.util.List;

import com.tw.ticket.controller.CartController.AddReqDto;
import com.tw.ticket.controller.CartController.CartDto;
import com.tw.ticket.controller.CartController.QuantityReqDto;

public interface TicketCartsRedis {

	/**
	 * 票券購物車清單 (Redis)
	 *
	 * @param sessionId
	 * @return
	 */
	public List<CartDto> getItems(final String sessionId);

	/**
	 * 放入購物車 (Redis)
	 *
	 * @param reqDto
	 * @return
	 */
	public int addItem(final AddReqDto reqDto);

	/**
	 * 變更數量 (Redis)
	 *
	 * @param reqDto
	 * @return
	 */
	public boolean updateItem(final QuantityReqDto reqDto);

	/**
	 * 移除購物車品項 (Redis)
	 *
	 * @param sessionId
	 * @param ticketId
	 */
	public void removeItem(final String sessionId, final int ticketId);

	/**
	 * 將Redis資料存到資料庫
	 *
	 * @param memberId
	 * @param sessionId
	 */
	public void convertItems(final int memberId, final String sessionId);
}
