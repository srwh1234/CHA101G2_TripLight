package com.tw.ticket.service;

import com.tw.ticket.controller.FavoriteController.FavoriteRequest;

public interface FavoriteService {

	// 更新我的最愛的狀態
	public int updateItem(FavoriteRequest request);
}
