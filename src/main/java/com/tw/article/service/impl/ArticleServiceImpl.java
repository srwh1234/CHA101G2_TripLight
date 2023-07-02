package com.tw.article.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;
import com.tw.article.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleServiceImpl(final ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@Override
	public Article createArticle(final Article article) {
		article.getArticleTypeId();
		final String articleTitle = article.getArticleTitle();
		if (articleTitle == null || articleTitle.trim().isEmpty()) {
			throw new IllegalArgumentException("標題不能為空");
		}
		final String articlePostContent = article.getArticlePostContent();
		if (articlePostContent == null || articlePostContent.trim().isEmpty()) {
			throw new IllegalArgumentException("內容不能為空");
		}
		return articleRepository.save(article);

	}

	@Override
	public Article updateArticle(final Article article) {
		article.getArticleTypeId();
		final String articleTitle = article.getArticleTitle();
		if (articleTitle == null || articleTitle.trim().isEmpty()) {
			throw new IllegalArgumentException("標題不能為空");
		}
		final String articlePostContent = article.getArticlePostContent();
		if (articlePostContent == null || articlePostContent.trim().isEmpty()) {
			throw new IllegalArgumentException("內容不能為空");
		}
		return articleRepository.save(article);
	}

	@Override
	public void deleteArticle(final Article article) {
		// return articleRepository.deleteArticle(article);;
	}

	@Override
	public Optional<Article> getArticle(final Integer articleId) {
		return articleRepository.findById(articleId);
	}

	@Override
	public Article findById(final Integer articleId) {
		// 实现通过文章ID查找文章的逻辑
		final Optional<Article> optionalArticle = articleRepository.findById(articleId);
		return optionalArticle.orElse(null);
	}

	@Override
	public Article save(final Article article) {
		return articleRepository.save(article);
	}

	@Override
	public List<Article> getAllArticle() {
		return articleRepository.findAll();
	}

	@Override
	public byte[] findPicture(final int id) {

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

	@Override
	public boolean uploadPicture(final MultipartFile file, final String json) {
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