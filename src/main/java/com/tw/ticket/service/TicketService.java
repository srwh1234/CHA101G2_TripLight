package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.TicketController.DescResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.controller.TicketController.SearchResponse;
import com.tw.ticket.controller.TicketDetailController.DetailResponse;

public interface TicketService {

	// 取得票券
	public DetailResponse getItem(int memberId, final int ticketId);

	// 隨機票券
	public List<DescResponse> getRandomItem();

	// 熱門票券
	public List<DescResponse> getHotItem();

	// 搜尋票券
	public SearchResponse getSearchItem(SearchRequest request);

	// 輸入地點取得票券
	List<DescResponse> getTicket(String destination);
}
