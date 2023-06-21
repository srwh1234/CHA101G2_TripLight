package com.tw.article.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;

@CrossOrigin(origins = "*")
@RestController
public class ArticleImageControllerExam {

	@Autowired
	private ArticleRepository articleRepository;

	// 顯示圖片 (http://localhost:8080/aimg/6)
	@GetMapping(value = "/aimg/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
	public byte[] articleImg(@PathVariable("imgUrl") final int id) {

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

	// 上傳圖片 (_test_articles.html)
	@PostMapping("/artiupload")
	public boolean uploadImg(//
			@RequestParam("image") final MultipartFile file, //
			@RequestParam("article") final String json) {

		// 以下都要放 service
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
