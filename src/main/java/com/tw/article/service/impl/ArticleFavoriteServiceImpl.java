package com.tw.article.service.impl;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.article.controller.ArticleFavoriteController.FavoriteReqDto;
import com.tw.article.model.Article;
import com.tw.article.model.ArticleFavorite;
import com.tw.article.dao.ArticleFavoriteRepository;
import com.tw.article.service.ArticleFavoriteService;
import com.tw.article.service.ArticleService;

@Service
public class ArticleFavoriteServiceImpl implements ArticleFavoriteService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ArticleFavoriteRepository articleFavoriteRepository;
	
	@Autowired
	private ArticleService articleService;

	@Override
	public int updateFavorite(FavoriteReqDto favoritereqDto) {
		Member member = memberRepository.findById(favoritereqDto.getMemberId()).orElse(null);
		
		if (member == null) {
			return FAVORITE_LOGIN;
		}
		
		Article article = articleService.findById(favoritereqDto.getMemberId());
		
		if (article == null) {
			return FAVORITE_ERROR;
		}
		
//		if (favoritereqDto.isFavorite()) {
//			articleFavoriteRepository.deleteById(favoritereqDto.getMemberId());
//			return FAVORITE_DEL_OK;
//		}
		
		ArticleFavorite favorite = new ArticleFavorite();
//		favorite.setKey(favoritereqDto.getMemberId(),article);
		favorite.setAddTime(Timestamp.from(Instant.now()));
		articleFavoriteRepository.save(favorite);
		
		return FAVORITE_ADD_OK;
	}
}
