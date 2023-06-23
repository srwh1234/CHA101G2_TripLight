package com.tw.ticket.service;

import com.tw.ticket.controller.FavoriteController.FavoriteReqDto;

public interface FavoriteService {

	public static int FAVORITE_ADD_OK = 1; // 成功加入我的最愛
	public static int FAVORITE_DEL_OK = 2; // 已從我的最愛移除
	public static int FAVORITE_LOGIN = 3; // 需要登入
	public static int FAVORITE_ERROR = 4; // 發生未知的錯誤

	/**
	 * 更新我的最愛的狀態
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	public int updateItem(FavoriteReqDto reqDto);
}
