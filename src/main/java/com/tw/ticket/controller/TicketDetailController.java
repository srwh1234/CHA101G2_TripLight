package com.tw.ticket.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketComment;
import com.tw.ticket.service.CommentService;
import com.tw.ticket.service.TicketService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class TicketDetailController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private CommentService commentService;

	/**
	 * 前台-票券細項頁面-票券內容
	 */
	@GetMapping("/ticketdetail")
	public DetailDto detail(@RequestParam final int memberId, @RequestParam final int ticketId) {
		return ticketService.getItem(memberId, ticketId);
	}

	/**
	 * 前台-票券細項頁面-顯示留言
	 */
	@PostMapping("/ticketcomments")
	public CommentDto comments(@RequestBody final PageReqDto reqDto) {
		return commentService.getItems(reqDto);
	}

	// 定義請求物件
	@Data
	public static class PageReqDto {
		private int ticketId;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class DetailDto {
		public DetailDto(final Ticket ticket) {
			this.ticketId = ticket.getTicketId();
			this.ticketType = ticket.getTicketType().getName();
			this.name = ticket.getName();
			this.price = ticket.getPrice();
			this.totalSales = ticket.getTotalSales();
			this.description = ticket.getDescription();
			this.content = ticket.getContent();
			this.note = ticket.getNote();
			this.supplierName = ticket.getSupplierName();
			this.city = ticket.getCity();
			this.latitude = ticket.getLatitude();
			this.longitude = ticket.getLongitude();
			this.rating = ticket.getRatingSum() / ticket.getRatingCount();
			this.ratingPerson = ticket.getRatingCount();
		}

		private long ticketId;
		private String ticketType;
		private String name;
		private int price;
		private int available;
		private int totalSales;
		private String description;
		private String content;
		private String note;
		private String supplierName;
		private String city;
		private double latitude;
		private double longitude;
		private double rating;
		private int ratingPerson;
		private boolean favorite;
		private List<String> images;
		private PromotionDto promotion;
	}

	@Data
	public static class PromotionDto {
		private int price;
		private Date startDate;
		private Date endDate;
	}

	@Data
	public static class CommentDto {
		private int allPage;
		private int curPage;
		private ArrayList<TicketComment> comments = new ArrayList<>();
	}

}
