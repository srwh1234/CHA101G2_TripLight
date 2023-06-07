package com.tw.ticket.service;

import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;

public interface BkTicketService {

	public SearchResponse getItems(SearchRequest request);
}
