package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.TicketDetailController.DetailResponse;
import com.tw.ticket.controller.TicketController.RadAndHotResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.controller.TicketController.SearchResponse;

public interface TicketService {

	// 取得票券
	public DetailResponse getItem(int id);

	// 隨機票券
	public List<RadAndHotResponse> getRandomItem();

	// 熱門票券
	public List<RadAndHotResponse> getHotItem();

	// 搜尋票券
	public SearchResponse getSearchItem(SearchRequest request);

	// 輸入地點取得票券
	List<RadAndHotResponse> getTicket(String destination);
}
