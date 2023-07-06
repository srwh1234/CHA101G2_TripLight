package com.tw.ticket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.TicketDetailController.PromotionDto;
import com.tw.ticket.model.Promotion;
import com.tw.ticket.model.PromotionDetail;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.dao.PromotionDetailRepository;
import com.tw.ticket.model.dao.PromotionRepository;
import com.tw.ticket.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;

	@Autowired
	private PromotionDetailRepository promotionDetailRepository;

	/**
	 * 取得全部的促銷
	 *
	 * @return
	 */
	@Override
	public List<Promotion> getItems() {
		return promotionRepository.findAll();
	}

	/**
	 * 取得促銷資料
	 *
	 * @param promotionId 促銷編號
	 * @return
	 */
	@Override
	public Promotion getItemById(final int promotionId) {
		return promotionRepository.findById(promotionId).orElse(null);
	}

	/**
	 * 新增促銷
	 *
	 * @param promotion 不含id的促銷物件
	 * @return
	 */
	@Override
	public boolean addItem(final Promotion promotion) {
		promotionRepository.save(promotion);
		return true;
	}

	/**
	 * 更新促銷
	 *
	 * @param promotion 含id的促銷物件
	 * @return
	 */
	@Override
	public boolean updateItem(final Promotion promotion) {
		promotionDetailRepository.deleteByKeyPromotionId(promotion.getPromotionId());
		promotionRepository.save(promotion);
		return true;
	}

	/**
	 * 取得指定票券的促銷資料
	 *
	 * @param ticketId 票券編號
	 * @return
	 */
	@Override
	public PromotionDto getItem(final int ticketId) {
		final List<PromotionDetail> details = promotionDetailRepository.findUsableByTicketId(ticketId);
		if (details.isEmpty()) {
			return null;
		}
		final PromotionDetail detail = details.get(0);

		if (detail == null) {
			return null;
		}
		final Promotion promotion = promotionRepository.findById(detail.getKey().getPromotionId()).orElse(null);
		if (promotion == null) {
			return null;
		}
		final PromotionDto dto = new PromotionDto();
		dto.setPrice(detail.getPromotionPrice());
		dto.setStartDate(promotion.getStartDate());
		dto.setEndDate(promotion.getEndDate());

		return dto;
	}

	/**
	 * 取得促銷價格 (如果沒有回傳原價)
	 *
	 * @param ticket 票券本人
	 * @return
	 */
	@Override
	public int getPrice(final Ticket ticket) {
		final List<PromotionDetail> details = promotionDetailRepository.findUsableByTicketId(ticket.getTicketId());
		if (details.isEmpty()) {
			return ticket.getPrice();
		}
		final PromotionDetail detail = details.get(0);

		if (detail == null) {
			return ticket.getPrice();
		}
		return detail.getPromotionPrice();
	}
}
