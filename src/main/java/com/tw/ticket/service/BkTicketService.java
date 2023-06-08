package com.tw.ticket.service;

import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.BkTicketController.TikcetRequest;
import com.tw.ticket.controller.TicketController.SearchRequest;

public interface BkTicketService {

	// 後台票券清單 (分頁)
	public SearchResponse getItems(SearchRequest request);

	// 後台新增票券
	public boolean addItems(TikcetRequest request);
}
