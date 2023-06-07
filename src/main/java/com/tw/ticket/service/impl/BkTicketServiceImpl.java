package com.tw.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.BkTicketController.TicketResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.service.BkTicketService;

@Service
public class BkTicketServiceImpl implements BkTicketService {

	@Autowired
	private TicketRepository repository;

	@Override
	public SearchResponse getItems(final SearchRequest request) {
		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);
		final Page<Ticket> page = repository.searchTicketByKeyword(	//
				request.getKeyword(),	// 關鍵字
				pageable				// 分頁物件
		);
		// 轉成自己定義的物件
		final SearchResponse response = new SearchResponse();
		response.setCurPage(request.getPage());
		response.setTotalPage(page.getTotalPages());

		page.getContent().forEach(ticket -> {
			final TicketResponse ticketResponse = new TicketResponse();
			ticketResponse.setTicketId(ticket.getTicketId());
			ticketResponse.setTicketName(ticket.getName());
			ticketResponse.setTicketType(ticket.getTicketType().getName());
			ticketResponse.setTicketCity(ticket.getCity());
			ticketResponse.setSupplierName(ticket.getSupplierName());
			ticketResponse.setAvailable(10);// XXX
			response.getTickets().add(ticketResponse);

		});
		return response;
	}

}
