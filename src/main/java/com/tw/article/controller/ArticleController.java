package com.tw.article.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;

@RestController
public class ArticleController {
	
	private final ArticleRepository articleRepository;
	// private final ArticleService articleService; ??
    
    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    
    // 定義GET請求處理方法：單一
    @GetMapping("/{articleId}")
    public Article getArticle(@PathVariable Integer articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
    }
    
    // 定義GET請求處理方法：全部
    public List<Article> getArticle(){
    	return articleRepository.findAll();
    }
    
    // 定義POST請求處理方法
    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        
    	// 在此處對文章進行保存、驗證或其他邏輯處理?
    	
        return articleRepository.save(article);
    }
    
    // 定義PUT請求處理方法
    @PutMapping("/{articleId}")
    public Article updateArticle(@PathVariable Integer articleId, @RequestBody Article updatedArticle) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
        return articleRepository.save(article);
    }
    
    // 定義DELETE請求處理方法
    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Integer articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
        articleRepository.delete(article);
    }
}
