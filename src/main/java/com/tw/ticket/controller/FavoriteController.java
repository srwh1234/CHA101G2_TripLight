package com.tw.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.FavoriteService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

	/**
	 * 前台-票券明細-我的最愛狀態切換
	 */
	@PostMapping("/ticketfavorite")
	public int ticketfavorite(@RequestBody final FavoriteReqDto reqDto) {
		return favoriteService.updateItem(reqDto);
	}

	// 定義請求物件
	@Data
	public static class FavoriteReqDto {
		private int memberId;
		private int ticketId;
		private boolean favorite;
	}
}
