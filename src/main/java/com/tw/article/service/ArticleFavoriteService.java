package com.tw.article.service;

import com.tw.article.controller.ArticleFavoriteController.FavoriteReqDto;

public interface ArticleFavoriteService {
	
	public static int FAVORITE_ADD_OK = 1; // 成功加入我的最愛
	public static int FAVORITE_DEL_OK = 2; // 已從我的最愛移除
	public static int FAVORITE_LOGIN = 3; // 需要登入帳號
	public static int FAVORITE_ERROR = 4; // 發生未知的錯誤
	
	
	public int updateFavorite(FavoriteReqDto favoritereqDto);
}
