package com.tw.article.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tw.article.controller.ArticleController.DataArticle;
import com.tw.article.model.Article;

public interface ArticleService {
	
	//新增文章
    Article createArticle(Article article);
    //更改文章
    Article updateArticle(Article article);
    //刪除文章，應該用不到，改定義為隱藏
    void deleteArticle(Article article);
    
    //查詢文章：單一
    Optional<Article> getArticle(Integer articleId);
    //查詢文章：全部
    List<Article> getAllArticle();
    
	Article findById(Integer articleId);
	
	Article save(Article article);
	
	public byte[] findPicture(final int id);
	
	public boolean uploadPicture(MultipartFile file, String json);
	
    
//	Article findByPrimaryKey(Integer articleId);
    
    
}

