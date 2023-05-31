package com.tw.ticket.service;

import com.tw.ticket.controller.DetailController.CommentResponse;

public interface CommentService {
	// 取得評論
	public CommentResponse getComment(long ticketId);
}
