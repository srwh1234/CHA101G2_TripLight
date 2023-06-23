package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.TicketController.DescTicketDto;
import com.tw.ticket.controller.TicketController.PageDto;
import com.tw.ticket.controller.TicketController.PageReqDto;
import com.tw.ticket.controller.TicketDetailController.DetailDto;
import com.tw.ticket.model.Ticket;

public interface TicketService {

	/**
	 * 取得票券明細
	 *
	 * @param memberId 會員編號 (取得我的最愛會用到)
	 * @param ticketId 票券編號
	 * @return
	 */
	public DetailDto getItem(int memberId, final int ticketId);

	/**
	 * 隨機票券 (隨機選4個)
	 *
	 * @return
	 */
	public List<DescTicketDto> getRandomItem();

	/**
	 * 熱門票券 (按照銷售量選8個)
	 *
	 * @return
	 */
	public List<DescTicketDto> getHotItem();

	/**
	 * 搜尋票券
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public PageDto getSearchItem(PageReqDto reqDto);

	/**
	 * 未促銷的票券清單
	 *
	 * @return
	 */
	public List<Ticket> getItemsWithoutPromote();

	/**
	 * 輸入地點取得票券 (AI 行程，地點搜尋票券)
	 *
	 * @param destination 目的地的名稱
	 * @return
	 */
	public List<DescTicketDto> getTicket(String destination);
}
