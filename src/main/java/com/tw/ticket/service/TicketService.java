package com.tw.ticket.service;

import java.util.List;

import com.tw.ticket.controller.TicketController.MappingResponse;

public interface TicketService {
	public List<MappingResponse> findRnd();
}
