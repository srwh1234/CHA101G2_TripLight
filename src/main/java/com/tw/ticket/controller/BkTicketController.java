package com.tw.ticket.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	// 新增票券
	@PostMapping("/addticket")
	public boolean addTicket(@RequestBody final TikcetDto dto) {
		return bkTicketService.addItems(dto);
	}

	// 上架下架票券
	@PostMapping("/enableticket")
	public boolean enableTicket(@RequestBody final Map<String, Object> map) {
		return bkTicketService.enableItems(map);
	}

	// 增加票券數量
	@PostMapping("/addticketcount")
	public boolean addTicketCount(@RequestBody final Map<String, Object> map) {
		return bkTicketService.addItemCount(map);
	}

	// 要編輯的票券
	@GetMapping("/findticket")
	public TikcetDto findTicket(@RequestParam("id") final int ticketId) {
		return bkTicketService.getItem(ticketId);
	}

	// 編輯票券
	@PostMapping("/editticket")
	public boolean editTicket(@RequestBody final TikcetDto dto) {
		return bkTicketService.updateItem(dto);
	}

	// 定義請求物件
	@Data
	public static class TikcetDto {
		private int ticketId;
		private String ticketType;
		private String name;
		private int price;
		private int available;
		private int totalSales;
		private Date expiryDate;
		private String description;
		private String content;
		private String note;
		private String supplierName;
		private String city;
		private String address;
		private double latitude;
		private double longitude;
		private int rating;
		private int ratingPerson;
		private List<String> images = new ArrayList<>();

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
		private int ticketStatus;
		private String ticketCity;
		private String supplierName;
		private int available;
	}
}
