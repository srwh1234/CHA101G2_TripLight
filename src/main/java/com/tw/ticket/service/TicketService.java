package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.DetailController.DetailResponse;
import com.tw.ticket.controller.TicketController.RadAndHotResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.controller.TicketController.SearchResponse;
import com.tw.ticket.model.Ticket;

public interface TicketService {

	// 取得票券
	public DetailResponse getTicket(long id);

	// 隨機票券
	public List<RadAndHotResponse> getRnd();

	// 熱門票券
	public List<RadAndHotResponse> getHot();

	// 搜尋票券
	public SearchResponse getSearch(SearchRequest searchRequest);

	// 輸入地點取得票券
	List<RadAndHotResponse> getTicket(String destination);
}
