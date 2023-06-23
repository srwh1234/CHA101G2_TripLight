package com.tw.ticket.service;

import com.tw.ticket.controller.TicketDetailController.PromotionDto;
import com.tw.ticket.model.Ticket;

public interface PromotionService {

	// 取得促銷資料
	public PromotionDto getItem(int ticketId);

	// 取得促銷價格 (如果沒有回傳原價)
	public int getPrice(Ticket ticket);
}
