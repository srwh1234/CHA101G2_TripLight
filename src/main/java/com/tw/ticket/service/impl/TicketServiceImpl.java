package com.tw.ticket.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.TicketController.MappingResponse;
import com.tw.ticket.dao.TikcetRepository;
import com.tw.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TikcetRepository repository;

	@Override
	public List<MappingResponse> findRnd() {

		final List<MappingResponse> result = new ArrayList<>();

		repository.findAll().forEach(ticket -> {
			result.add(new MappingResponse(ticket));
		});

		return result;
	}

}
