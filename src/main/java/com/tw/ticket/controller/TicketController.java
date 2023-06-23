package com.tw.ticket.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.controller.TicketDetailController.PromotionDto;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.TicketService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;

	/**
	 * 前台-票券列表-隨機票券
	 */
	@GetMapping("/rndtickets")
	public List<DescTicketDto> randomTickets() {
		return ticketService.getRandomItem();
	}

	/**
	 * 前台-票券列表-熱門票券
	 */
	@GetMapping("/hottickets")
	public List<DescTicketDto> hotTickets() {
		return ticketService.getHotItem();
	}

	/**
	 * 前台-票券列表-搜尋票券
	 */
	@PostMapping("/searchtickets")
	public PageDto searchTickets(@RequestBody final PageReqDto reqDto) {
		return ticketService.getSearchItem(reqDto);
	}

	/**
	 * 後台-設定促銷-未促銷的票券清單
	 */
	@GetMapping("promotetickets")
	public List<Ticket> ticketsWithoutPromote() {
		return ticketService.getItemsWithoutPromote();
	}

	// 定義請求物件
	@Data
	public static class PageReqDto {
		private String keyword;
		private String[] types;
		private String[] cities;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class PageDto {
		private int curPage;
		private int totalPage;
		private ArrayList<DescTicketDto> tickets = new ArrayList<>();
	}

	@Data
	public static class DescTicketDto {
		public DescTicketDto(final Ticket ticket) {
			this.ticketId = ticket.getTicketId();
			this.name = ticket.getName();
			this.price = ticket.getPrice();
			this.rating = ticket.getRatingSum() / ticket.getRatingCount();
			this.ratingPerson = ticket.getRatingCount();
			this.city = ticket.getCity();
			this.description = ticket.getDescription();
		}

		private final long ticketId;
		private final String name;
		private final int price;
		private int available;
		private final int rating;
		private final int ratingPerson;
		private final String city;
		private final String description;
		private String image;
		private boolean favorite;
		private PromotionDto promotion;
	}
}
