package com.tw.article.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tw.article.model.ArticleImage;

public interface ArticleImageService {
	
	String addArticleImage(int id, MultipartFile file);
	
	ArticleImage updateArticleImage(Integer id, ArticleImage articleimage);
	
	boolean deleteArticleImage (Integer articleimageId);
	
	List<ArticleImage> getAllArticles();

	ArticleImage getArticleImageById(Integer articleImageId);
	
}
