package com.tw.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tw.article.dao.ArticleImageRepository;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;
import com.tw.article.service.ArticleService;
import com.tw.ticket.model.TicketImage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article createArticle(Article article) {
    	article.getArticleTypeId();
    	String articleTitle = article.getArticleTitle();
    	if (articleTitle == null || articleTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("標題不能為空");
        } 
        String articlePostContent = article.getArticlePostContent();
        if (articlePostContent == null || articlePostContent.trim().isEmpty()) {
            throw new IllegalArgumentException("內容不能為空");
        } 
			return articleRepository.save(article);
		
    }
    @Override
    public Article updateArticle(Article article) {
    	article.getArticleTypeId();
    	String articleTitle = article.getArticleTitle();
    	if (articleTitle == null || articleTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("標題不能為空");
        } 
        String articlePostContent = article.getArticlePostContent();
        if (articlePostContent == null || articlePostContent.trim().isEmpty()) {
            throw new IllegalArgumentException("內容不能為空");
        } 
			return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Integer articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public Optional<Article> getArticle(Integer articleId) {
        return articleRepository.findById(articleId);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

}