package com.tw.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tw.member.service.MyFavoriteService;
import com.tw.ticket.controller.OrderDetailController.DetailRequest;
import com.tw.ticket.controller.OrderDetailController.DetailResponse;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketFavorite;
import com.tw.ticket.service.FavoriteService;

import lombok.Data;

@Controller
public class MyFavoriteController {
	@Autowired
	private MyFavoriteService myFavoriteService;
	@Autowired
	private FavoriteService favoriteService;

	
	@GetMapping("/ticketFavoriteDetails/{id}")
	public List<Ticket> ticketFavorites(@PathVariable("id") int id){
		return myFavoriteService.getTicket(id);
	}
	
	
//	@Data
//	public static class TicketFaovriteDetail{
//		private Integer ticketId;
//		
//		private String name;// 名稱
//
//		private Integer price;// 單價
//		
//		private String description;// 票券描述 (50字以內)
//
//
//	}
}
