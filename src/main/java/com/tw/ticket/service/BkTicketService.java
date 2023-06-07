package com.tw.ticket.service;

import com.tw.ticket.controller.BkTicketController.PageResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;

public interface BkTicketService {

	public PageResponse getItems(SearchRequest request);
}
