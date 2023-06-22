package com.tw.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.controller.TicketDetailController.PromotionDto;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.CartService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class CartController {

	@Autowired
	private CartService cartService;

	/**
	 * 前台-購物車-購物車清單
	 */
	@GetMapping("/carts")
	public List<CartDto> carts(@RequestParam final int memberId) {
		return cartService.getItems(memberId);
	}

	/**
	 * 前台-票券明細-加入購物車
	 */
	@PostMapping("/addcart")
	public int addCart(@RequestBody final AddReqDto reqDto) {
		return cartService.addItem(reqDto);
	}

	/**
	 * 前台-購物車-變更數量
	 */
	@PostMapping("/modifycart")
	public boolean modifyCart(@RequestBody final QuantityReqDto reqDto) {
		return cartService.updateItem(reqDto);
	}

	/**
	 * 前台-購物車-移除
	 */
	@GetMapping("/removecart")
	public boolean removeCart(@RequestParam final int memberId, @RequestParam final int ticketId) {
		return cartService.removeItem(memberId, ticketId);
	}

	// 定義請求物件
	@Data
	public static class AddReqDto {
		private int memberId;
		private int ticketId;
		private int quantity;
	}

	@Data
	public static class QuantityReqDto {
		private int memberId;
		private int ticketId;
		private int modify;
	}

	// 定義回傳物件
	@Data
	public static class CartDto {
		private int quantity;
		private DescTicketDto ticket;
	}

	@Data
	public static class DescTicketDto {
		public DescTicketDto(final Ticket ticket) {
			this.ticketId = ticket.getTicketId();
			this.name = ticket.getName();
			this.price = ticket.getPrice();
			this.description = ticket.getDescription();
		}

		private int ticketId;
		private String name;
		private int price;
		private int available;
		private String description;
		private String image;
		private PromotionDto promotion;
	}
}
