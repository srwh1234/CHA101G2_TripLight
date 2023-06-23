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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tw.ticket.controller.TicketController.PageReqDto;
import com.tw.ticket.service.BkTicketService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bk")
public class BkTicketController {

	@Autowired
	private BkTicketService bkTicketService;

	/**
	 * 後台-票券資料管理-票券清單分頁
	 */
	@PostMapping("/searchtickets")
	public PageDto searchTickets(@RequestBody final PageReqDto reqDto) {
		return bkTicketService.getItems(reqDto);
	}

	/**
	 * 後台-票券資料管理-上架下架票券
	 */
	@PostMapping("/enableticket")
	public boolean enableTicket(@RequestBody final Map<String, Object> map) {
		return bkTicketService.enableItems(map);
	}

	/**
	 * 後台-票券資料管理-增加票券數量
	 */
	@PostMapping("/addticketcount")
	public boolean addTicketCount(@RequestBody final Map<String, Object> map) {
		return bkTicketService.addItemCount(map);
	}

	/**
	 * 後台-票券資料管理-新增票券
	 */
	@PostMapping("/addticketform")
	public boolean addTicketForm(//
			@RequestParam("post") final String jsonString,//
			@RequestPart("images") final MultipartFile[] files) {
		return bkTicketService.addItems(jsonString, files);
	}

	/**
	 * 後台-票券資料管理-要編輯的票券資料
	 */
	@GetMapping("/findticket")
	public TikcetDto findTicket(@RequestParam("id") final int ticketId) {
		return bkTicketService.getItem(ticketId);
	}

	/**
	 * 後台-票券資料管理-編輯票券
	 */
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
	public static class PageDto {
		private int curPage;
		private int totalPage;
		private List<DescTicketDto> tickets = new ArrayList<>();
	}

	@Data
	public static class DescTicketDto {
		private int ticketId;
		private String ticketName;
		private String ticketType;
		private int ticketStatus;
		private String ticketCity;
		private String supplierName;
		private int available;
	}
}
