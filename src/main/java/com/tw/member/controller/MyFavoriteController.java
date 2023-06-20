package com.tw.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.service.MyFavoriteService;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.FavoriteService;

@RestController
public class MyFavoriteController {
	@Autowired
	private MyFavoriteService myFavoriteService;
	@Autowired
	private FavoriteService favoriteService;

	
	@GetMapping("/tickets/{id}")
	public List<Ticket> ticketFavorites(@PathVariable("id") int id){
		System.out.println("Controller");
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
