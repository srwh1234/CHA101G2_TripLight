package com.tw.ticket.controller;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketComment;
import com.tw.ticket.service.CommentService;
import com.tw.ticket.service.TicketService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/TripLight")
public class DetailController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/ticketdetail")
	public DetailResponse test(@RequestParam("id") final Long id) {

		// test
		commentService.getComment(id);

		return ticketService.getTicket(id);
	}

	// 定義回傳物件
	@Data
	public static class DetailResponse {
		public DetailResponse(final Ticket ticket) {
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
			this.images = ticket.getImgUrlExs();
		}

		private int ticketId;
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
		private ArrayList<String> images;
		private PromotionResponse promotion;// XXX 未完成
	}

	@Data
	public static class PromotionResponse {
		private int price;
		private Date startDate;
		private Date endDate;
	}

	@Data
	public static class CommentResponse {
		private int allPage;
		private int curPage;
		private ArrayList<TicketComment> comments = new ArrayList<>();
	}
}
