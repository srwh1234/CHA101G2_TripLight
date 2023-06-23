package com.tw.ticket.service;

import com.tw.ticket.controller.TicketDetailController.CommentDto;
import com.tw.ticket.controller.TicketDetailController.PageReqDto;

public interface CommentService {

	/**
	 * 取得票券評論
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public CommentDto getItems(PageReqDto reqDto);
}
