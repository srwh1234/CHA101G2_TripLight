package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.TicketDetailController.PromotionDto;
import com.tw.ticket.model.Promotion;
import com.tw.ticket.model.Ticket;

public interface PromotionService {

	/**
	 * 取得全部的促銷
	 *
	 * @return
	 */
	public List<Promotion> getItems();

	/**
	 * 取得促銷資料
	 *
	 * @param promotionId 促銷編號
	 * @return
	 */
	public Promotion getItemById(int promotionId);

	/**
	 * 新增促銷
	 *
	 * @param promotion 不含id的促銷物件
	 * @return
	 */
	public boolean addItem(Promotion promotion);

	/**
	 * 更新促銷
	 *
	 * @param promotion 含id的促銷物件
	 * @return
	 */
	public boolean updateItem(Promotion promotion);

	/**
	 * 取得指定票券的促銷資料
	 *
	 * @param ticketId 票券編號
	 * @return
	 */
	public PromotionDto getItem(int ticketId);

	/**
	 * 取得促銷價格 (如果沒有回傳原價)
	 *
	 * @param ticket 票券本人
	 * @return
	 */
	public int getPrice(Ticket ticket);
}
