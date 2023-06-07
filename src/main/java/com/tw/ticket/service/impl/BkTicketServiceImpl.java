package com.tw.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.BkTicketController.PageResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.service.BkTicketService;

@Service
public class BkTicketServiceImpl implements BkTicketService {

	@Autowired
	private TicketRepository repository;

	@Override
	public PageResponse getItems(final SearchRequest request) {
		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);
		final Page<Ticket> page = repository.searchTicketByKeyword(	//
				request.getKeyword(),	// 關鍵字
				pageable				// 分頁物件
		);
		System.out.println(page);
		return null;
	}

}
