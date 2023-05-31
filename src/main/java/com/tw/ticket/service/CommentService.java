package com.tw.ticket.service;

import com.tw.ticket.controller.DetailController.CommentResponse;
import com.tw.ticket.controller.DetailController.SearchRequest;

public interface CommentService {

	// 取得評論
	public CommentResponse getComment(SearchRequest searchRequest);
}
