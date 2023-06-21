package com.tw.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;
import com.tw.article.service.ArticleService;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService { 
	
	public static String IMG_URL = "http://localhost:8080/img/";
	
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
    public void deleteArticle(Article article) {
//       return articleRepository.deleteArticle(article);;
    }

    @Override
    public Optional<Article> getArticle(Integer articleId) {
        return articleRepository.findById(articleId);
    }
   

    @Override
    public Article findById(Integer articleId) {
        // 实现通过文章ID查找文章的逻辑
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        return optionalArticle.orElse(null);
    }
    
    @Override
	public Article save(Article article) {
    	return articleRepository.save(article);
    }

	@Override
	public List<Article> getAllArticle() {
		 return articleRepository.findAll();
	}
    
	@Override
	public byte[] findPicture (final int id) {

		// 以下都要放 service
		final Article article = articleRepository.findById(id).orElse(null);
		// 沒有指定編號的圖片
		if (article == null) {
			final ClassPathResource resource = new ClassPathResource("images/bb.gif");

			byte[] bytes = null;
			try (final InputStream is = resource.getInputStream();) {
				bytes = new byte[is.available()];
				is.read(bytes);

			} catch (final IOException e) {
				e.printStackTrace();
			}
			return bytes;
		}
		return article.getArticlePicture();
	}
	
	public boolean uploadPicture(MultipartFile file, String json) {
		try {
			final Article article = new ObjectMapper().readValue(json, Article.class);
			article.setArticlePostTime(new Timestamp(System.currentTimeMillis()));
			article.setArticlePicture(file.getBytes());

			articleRepository.save(article);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}