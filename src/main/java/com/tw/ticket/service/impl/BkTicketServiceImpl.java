package com.tw.ticket.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.BkTicketController.TicketResponse;
import com.tw.ticket.controller.BkTicketController.TikcetRequest;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.service.BkTicketService;

@Service
public class BkTicketServiceImpl implements BkTicketService {

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketSnRepository snRepository;

	// 後台新增票券
	@Override
	public boolean addItems(final TikcetRequest request) {

		for (final String base64Str : request.getImages()) {
			final byte[] array = Base64.getDecoder().decode(base64Str);

			System.out.println(array.length);
		}
		return true;
	}

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

			final int available = snRepository.countUsableSn(ticket.getTicketId());

			final TicketResponse ticketResponse = new TicketResponse();
			ticketResponse.setTicketId(ticket.getTicketId());
			ticketResponse.setTicketName(ticket.getName());
			ticketResponse.setTicketType(ticket.getTicketType().getName());
			ticketResponse.setTicketCity(ticket.getCity());
			ticketResponse.setSupplierName(ticket.getSupplierName());
			ticketResponse.setAvailable(available);
			response.getTickets().add(ticketResponse);

		});
		return response;
	}

}
