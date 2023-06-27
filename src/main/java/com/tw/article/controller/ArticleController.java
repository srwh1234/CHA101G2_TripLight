package com.tw.article.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.article.model.Article;
import com.tw.article.service.ArticleService;
import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/article")
public class ArticleController {

	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Autowired
	private MemberRepository memberRepository;

	// 定義GET請求處理方法：單一
	@GetMapping("/articleId/{id}")
	public Article getArticle(@PathVariable("id") Integer articleId) {

		System.out.println(articleId);
		
		Article article = articleService.findById(articleId);
		
		Member member = memberRepository.findById(article.getMemberId()).orElse(null);

		if (member != null) {
			DataArticle dataArticle = new DataArticle(article);
			dataArticle.setMemberName(member.getMemberNameFirst() + member.getMemberNameLast());
			article.setMemberName(dataArticle.getMemberName());
		}
		return article;
	}
	
	// 定義GET請求處理方法：全部
	@GetMapping("/articles")
	public List<DataArticle> getAllArticles() {

		List<DataArticle> result = new ArrayList<>();

		for (Article article : articleService.getAllArticle()) {

			Member member = memberRepository.findByMemberId(article.getMemberId());

			if (member != null) {
				DataArticle dataArticle = new DataArticle(article);
				dataArticle.setMemberName(member.getMemberNameFirst() + member.getMemberNameLast());
				result.add(dataArticle);
			}
		}
		return result;
	}

	// 定義POST請求處理方法
	@PostMapping("/addarticle")
	public Article addarticle(@RequestParam("image") MultipartFile file, @RequestParam("article") String jsonString) {
		Article article = null;

		try {
			article = new ObjectMapper().readValue(jsonString, Article.class);
			article.setArticlePicture(file.getBytes());
			article.setArticlePostTime(new Timestamp(System.currentTimeMillis()));
			articleService.save(article);

		} catch (IOException e) {

			e.printStackTrace();
		}
		return article;
	}

	// 定義PUT請求處理方法
	@PutMapping("/{articleId}")
	public Article updateArticle(@PathVariable Integer articleId, @RequestBody Article updatedArticle) {
		Article article = articleService.findById(articleId);
//                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
		return articleService.save(article);
	}

	// 定義DELETE請求處理方法
	@DeleteMapping("/{articleId}")
	public void deleteArticle(@PathVariable Integer articleId) {
		Article article = articleService.findById(articleId);
//                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
		articleService.deleteArticle(article);
	}

	public List<Article> getArticleService() {
		return articleService.getAllArticle();
	}

	@GetMapping(value = "/article/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
	public byte[] findPicture(@PathVariable("imgUrl") final int id) {
		return articleService.findPicture(id);
	}

	@PostMapping("/artiupload")
	public boolean uploadPicture(//
			@RequestParam("image") final MultipartFile file, //
			@RequestParam("article") final String json) {
		return articleService.uploadPicture(file, json);
	}

	@Data
	public static class DataArticle {

		public DataArticle(Article article) {
			this.articleId = article.getArticleId();
			this.articleTypeId = article.getArticleTypeId();
			this.articleTitle = article.getArticleTitle();
			this.articlePostContent = article.getArticlePostContent();
			this.articlePostTime = article.getArticlePostTime();
			this.articleViews = article.getArticleViews();
			this.articlePicture = article.getArticlePicture();
//			this.articleLikesCount = 100;
		}

		private Integer articleId;
		private String memberName;
		private Integer articleTypeId;
		private String articleTitle;
		private String articlePostContent;
		private Timestamp articlePostTime;
		private Integer articleViews;
		private byte[] articlePicture;
//		private Integer articleLikesCount;

	}
}
