package com.tw.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.article.model.Article;
import com.tw.member.service.MyFavoriteService;
import com.tw.member.service.MyFavoriteService.TicketDTO;
import com.tw.member.service.MyFavoriteService.TripDTO;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.FavoriteService;
import com.tw.ticket.service.impl.ImageServiceImpl;

@RestController
public class MyFavoriteController {
	@Autowired
	private MyFavoriteService myFavoriteService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ImageServiceImpl ticketImageServiceImpl;
	
	//票券=========================================
	// 載入票券

	@GetMapping("/tickets/{id}")
	public List<TicketDTO> ticketFavorites(@PathVariable("id") int id) {
		return myFavoriteService.getTicket(id);
	}


	// 刪除票券
	 @PostMapping("/removeTicket")
	public boolean removeCart( 
			@RequestParam("memberId") final int memberId, 
			@RequestParam("ticketId") final int ticketId) {
		return myFavoriteService.removeItem(memberId, ticketId);
	}
	//旅行團=========================================
		@GetMapping("/groups/{id}")
		public List<TripDTO> tripFavorites(@PathVariable("id") int id) {
			return myFavoriteService.getTrip(id);
		}


		// 刪除票券
		 @PostMapping("/removeTrip")
		public boolean removeTrip( 
				@RequestParam("memberId") final int memberId, 
				@RequestParam("tripId") final int tripId) {
			 //System.out.println("ddddddddddddd");
			return myFavoriteService.removeTrip(memberId, tripId);
		}

}
