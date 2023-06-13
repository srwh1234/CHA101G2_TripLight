package com.tw.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tw.article.model.ArticleImage;
import com.tw.article.service.ArticleImageService;

@RestController
@RequestMapping("/article/images")
public class ArticleImageController {

    private final ArticleImageService articleImageService;

    @Autowired
    public ArticleImageController(ArticleImageService articleImageService) {
        this.articleImageService = articleImageService;
    }

    @PostMapping("/article/images")
	public String addArticleImage(//
			@RequestParam("id") final int articleId,//
			@RequestPart("image") final MultipartFile file) {
		return addArticleImage(articleId, file);
	}

    @GetMapping("/{id}")
    public ResponseEntity<ArticleImage> getArticleImageById(@PathVariable("id") Integer id) {
        ArticleImage articleImage = articleImageService.getArticleImageById(id);
        if (articleImage != null) {
            return new ResponseEntity<>(articleImage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleImage> updateArticleImage(@PathVariable("id") Integer id, @RequestBody ArticleImage articleImage) {
        ArticleImage updatedArticleImage = articleImageService.updateArticleImage(id, articleImage);
        if (updatedArticleImage != null) {
            return new ResponseEntity<>(updatedArticleImage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleImage(@PathVariable("id") Integer id) {
        boolean deleted = articleImageService.deleteArticleImage(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
