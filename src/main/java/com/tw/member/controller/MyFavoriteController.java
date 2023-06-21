package com.tw.member.controller;

import java.util.List;

import org.hibernate.property.access.spi.GetterMethodImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.service.MyFavoriteService;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketFavorite;
import com.tw.ticket.service.FavoriteService;

@RestController
public class MyFavoriteController {
	@Autowired
	private MyFavoriteService myFavoriteService;
	@Autowired
	private FavoriteService favoriteService;

	// 載入票券
	@GetMapping("/tickets/{id}")
	public List<Ticket> ticketFavorites(@PathVariable("id") int id) {
		System.out.println("Controller");
		return myFavoriteService.getTicket(id);
	}

	// 刪除票券

//	@DeleteMapping("/removeTicket/{ticketId}")
//	public boolean removeTicket(@PathVariable Ticket ticketId) {
//		System.out.println("33333333control");
//		return myFavoriteService.removeTicket(ticketId);
//	}
//	 @DeleteMapping("/removeTicket/{id}")
//	    public boolean removeTicket(@PathVariable int id) {
//	        // 創建PrimaryKey對象
//	        TicketFavorite.PrimaryKey key = new TicketFavorite.PrimaryKey();
//	        key.setMemberId(); // 根據您的需求設置會員編號
//	        // 創建Ticket對象並設置ticketId
//	        Ticket ticket = new Ticket();
//	        ticket.setId(id);
//	        key.setTicket(ticket);
//
//	        myFavoriteService.deleteByKeyTicket(key);
//	        return true;
//	    }
	 @PostMapping("/removeTicket")
	public boolean removeCart( 
			@RequestParam("memberId") final int memberId, 
			@RequestParam("ticketId") final int ticketId) {
		return myFavoriteService.removeItem(memberId, ticketId);
	}
}
