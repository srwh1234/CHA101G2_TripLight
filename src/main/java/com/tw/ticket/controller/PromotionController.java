package com.tw.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.model.Promotion;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.PromotionService;
import com.tw.ticket.service.TicketService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bk")
public class PromotionController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private PromotionService promotionService;

	/**
	 * 後台-設定促銷-獲得促銷清單
	 */
	@GetMapping("/promotions")
	public List<Promotion> promotions() {
		return promotionService.getItems();
	}

	/**
	 * 後台-設定促銷-獲得指定促銷
	 */
	@GetMapping("/promotion/{id}")
	public Promotion promotion(@PathVariable final int id) {
		return promotionService.getItemById(id);
	}

	/**
	 * 後台-設定促銷-新增促銷
	 */
	@PostMapping("/addpromotion")
	public boolean addPromotion(@RequestBody final Promotion promotion) {
		return promotionService.addItem(promotion);
	}

	/**
	 * 後台-設定促銷-編輯促銷
	 */
	@PostMapping("/updatepromotion")
	public boolean updatepromotion(@RequestBody final Promotion promotion) {
		return promotionService.updateItem(promotion);
	}

	/**
	 * 後台-設定促銷-下拉式選單-未促銷的票券清單
	 */
	@GetMapping("/promotetickets")
	public List<Ticket> ticketsWithoutPromote() {
		return ticketService.getItemsWithoutPromote();
	}
}
