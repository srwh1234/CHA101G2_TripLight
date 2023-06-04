package com.tw.ticket.service;

import com.tw.ticket.controller.TicketDetailController.CommentResponse;
import com.tw.ticket.controller.TicketDetailController.SearchRequest;

public interface CommentService {

	// 取得評論
	public CommentResponse getItems(SearchRequest request);
}
