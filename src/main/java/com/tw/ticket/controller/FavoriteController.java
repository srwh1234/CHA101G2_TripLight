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

	public static int FAVORITE_ADD_OK = 1; // 成功加入我的最愛
	public static int FAVORITE_DEL_OK = 2; // 已從我的最愛移除
	public static int FAVORITE_LOGIN = 3; // 需要登入
	public static int FAVORITE_ERROR = 4; // 發生未知的錯誤

	@Autowired
	private FavoriteService favoriteService;

	@PostMapping("/ticketfavorite")
	public int ticketfavorite(@RequestBody final FavoriteRequest request) {
		return favoriteService.updateItem(request);
	}

	// 定義請求物件
	@Data
	public static class FavoriteRequest {
		private int memberId;
		private int ticketId;
		private boolean favorite;
	}
}
