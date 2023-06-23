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

	// 取得促銷資料
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

	// 取得促銷價格 (如果沒有回傳原價)
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
