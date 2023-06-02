package com.tw.ticket.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.TicketService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;

	// 隨機票券
	@GetMapping("/rndtickets")
	public List<RadAndHotResponse> randomTickets() {
		return ticketService.getRandomItem();
	}

	// 熱門票券
	@GetMapping("/hottickets")
	public List<RadAndHotResponse> hotTickets() {
		return ticketService.getHotItem();
	}

	// 搜尋票券
	@PostMapping("/searchtickets")
	public SearchResponse searchTickets(@RequestBody final SearchRequest searchRequest) {
		return ticketService.getSearchItem(searchRequest);
	}

	// 定義請求物件
	@Data
	public static class SearchRequest {
		private String keyword;
		private String[] types;
		private String[] cities;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class SearchResponse {
		private int curPage;
		private int totalPage;
		private ArrayList<RadAndHotResponse> tickets = new ArrayList<>();
	}

	@Data
	public static class RadAndHotResponse {
		public RadAndHotResponse(final Ticket ticket) {
			this.ticketId = ticket.getTicketId();
			this.name = ticket.getName();
			this.price = ticket.getPrice();
			this.rating = ticket.getRatingSum() / ticket.getRatingCount();
			this.ratingPerson = ticket.getRatingCount();
			this.city = ticket.getCity();
			this.description = ticket.getDescription();
			this.image = ticket.getImgUrlEx(0);
		}

		private final long ticketId;
		private final String name;
		private final int price;
		private int available;
		private final int rating;
		private final int ratingPerson;
		private final String city;
		private final String description;
		private final String image;
	}
}
