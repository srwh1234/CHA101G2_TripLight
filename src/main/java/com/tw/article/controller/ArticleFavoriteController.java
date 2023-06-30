package com.tw.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.article.service.ArticleFavoriteService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class ArticleFavoriteController {
	
	@Autowired
	private ArticleFavoriteService articleFavoriteService;
	
	@PostMapping("/articlefavorite")
	public int articlefavorite(@RequestBody final FavoriteReqDto reqDto) {
		return articleFavoriteService.updateFavorite(reqDto);
	}
	
	@Data
	public static class FavoriteReqDto {
		private int memberId;
		private int ArticleId;
		private boolean favorite;
	}
}
