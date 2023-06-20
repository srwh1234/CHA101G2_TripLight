package com.tw.article.service;

import org.springframework.web.multipart.MultipartFile;

import com.tw.article.model.ArticleImage;

public interface ArticleImageService {
	
	public String addArticleImage(int id, MultipartFile file);
	
	public ArticleImage updateArticleImage(int articleimageid, ArticleImage articleimage);
	
	boolean deleteArticleImage (Integer articleimageId);
	
//	List<ArticleImage> getAllArticles();

	public static ArticleImage getArticleImage(Integer articleImageId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
