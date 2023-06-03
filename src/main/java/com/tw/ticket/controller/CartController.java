package com.tw.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.controller.DetailController.PromotionResponse;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.service.CartService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class CartController {

	public static int ADD_CART_OK = 1; // 添加成功
	public static int ADD_CART_SOLDOUT = 2;// 此商品數量不足
	public static int ADD_CART_ERROR = 3;// 發生未知的錯誤

	@Autowired
	private CartService cartService;

	// 票券購物車車
	@GetMapping("/carts")
	public List<CartResponse> carts(@RequestParam("memberId") final int memberId) {
		return cartService.getItems(memberId);
	}

	// 加入購物車
	@PostMapping("/addcart")
	public int addCart(@RequestBody final CartRequest cartRequest) {
		return cartService.addItem(cartRequest);
	}

	// 變更購物車數量
	@PostMapping("/modifycart")
	public boolean modifyCart(@RequestBody final ModifyRequest modifyRequest) {
		return cartService.updateItem(modifyRequest);
	}

	// 移除購物車物件
	@GetMapping("/removecart")
	public boolean removeCart( //
			@RequestParam("memberId") final int memberId, //
			@RequestParam("ticketId") final int ticketId) {
		return cartService.removeItem(memberId, ticketId);
	}

	// 定義請求物件
	@Data
	public static class CartRequest {
		private int memberId;
		private int ticketId;
		private int quantity;
	}

	@Data
	public static class ModifyRequest {
		private int memberId;
		private int ticketId;
		private int modify;
	}

	// 定義回傳物件
	@Data
	public static class CartResponse {
		private int quantity;
		private CartTicketResponse ticket;
	}

	@Data
	public static class CartTicketResponse {
		public CartTicketResponse(final Ticket ticket) {
			this.ticketId = ticket.getTicketId();
			this.name = ticket.getName();
			this.price = ticket.getPrice();
			this.description = ticket.getDescription();
			this.image = ticket.getImgUrlEx(0);
		}

		private int ticketId;
		private String name;
		private int price;
		private int available;
		private String description;
		private String image;
		private PromotionResponse promotion;// XXX 未完成
	}
}
