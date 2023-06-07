package com.tw.ticket.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.service.BkTicketService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bk")
public class BkTicketController {

	@Autowired
	private BkTicketService bkTicketService;

	// 後台票券清單
	@PostMapping("/searchtickets")
	public SearchResponse searchTickets(@RequestBody final SearchRequest request) {
		return bkTicketService.getItems(request);
	}

	// 定義回傳物件
	@Data
	public static class SearchResponse {
		private int curPage;
		private int totalPage;
		private List<TicketResponse> tickets = new ArrayList<>();
	}

	@Data
	public static class TicketResponse {
		private int ticketId;
		private String ticketName;
		private String ticketType;
		private String ticketCity;
		private String supplierName;
		private int available;
	}
}
