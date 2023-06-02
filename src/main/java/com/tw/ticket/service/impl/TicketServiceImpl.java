package com.tw.ticket.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.DetailController.DetailResponse;
import com.tw.ticket.controller.TicketController.RadAndHotResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.controller.TicketController.SearchResponse;
import com.tw.ticket.dao.TicketRepository;
import com.tw.ticket.dao.TicketSnRepository;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketSnRepository snRepository;

	// 取得票券明細
	@Override
	public DetailResponse getItem(final int id) {
		final Ticket ticket = repository.findById(id).orElse(null);

		if (ticket == null) {
			return null;
		}
		final DetailResponse detailResponse = new DetailResponse(ticket);

		// 可用數量
		detailResponse.setAvailable(snRepository.countUsableSn(id));
		return detailResponse;
	}

	// 隨機票券
	@Override
	public List<RadAndHotResponse> getRandomItem() {
		final List<RadAndHotResponse> result = new ArrayList<>();

		repository.findAll().forEach(ticket -> {
			if (result.size() >= 4) {
				return;
			}
			result.add(new RadAndHotResponse(ticket));
		});
		return result;
	}

	// 熱門票券
	@Override
	public List<RadAndHotResponse> getHotItem() {
		final List<RadAndHotResponse> result = new ArrayList<>();

		repository.findAllByOrderByTotalSalesDesc().forEach(ticket -> {
			if (result.size() >= 8) {
				return;
			}
			result.add(new RadAndHotResponse(ticket));

		});
		return result;
	}

	// 搜尋票券
	@Override
	public SearchResponse getSearchItem(final SearchRequest searchRequest) {
		final Pageable pageable = PageRequest.of(	//
				searchRequest.getPage(),	// 查詢的頁數，從0起算
				searchRequest.getSize()		// 查詢的每頁筆數
		);

		final Page<Ticket> page = repository.searchTicketByKeyword(	//
				searchRequest.getKeyword(),	// 關鍵字
				searchRequest.getTypes(),	// 類型
				searchRequest.getCities(),	// 縣市
				pageable					// 分頁物件
		);

		// 轉成自己定義的物件
		final SearchResponse searchResponse = new SearchResponse();
		searchResponse.setCurPage(searchRequest.getPage());
		searchResponse.setTotalPage(page.getTotalPages());

		page.getContent().forEach(ticket -> {
			searchResponse.getTickets().add(new RadAndHotResponse(ticket));
		});

		return searchResponse;
	}

	// AI 行程，地點搜尋票券
	@Override
	public List<RadAndHotResponse> getTicket(final String destination) {
		final List<RadAndHotResponse> result = new ArrayList<>();

		repository.findByCityContaining(destination).forEach(ticket -> {
			result.add(new RadAndHotResponse(ticket));
		});
		return result;
	}

}
