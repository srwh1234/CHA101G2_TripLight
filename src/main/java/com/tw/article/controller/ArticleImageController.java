package com.tw.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tw.article.model.ArticleImage;
import com.tw.article.service.ArticleImageService;

@Controller
@RestController("/article_image")
public class ArticleImageController {

    private final ArticleImageService articleImageService;

    @Autowired
    public ArticleImageController(ArticleImageService articleImageService) {
        this.articleImageService = articleImageService;
    }

    @PostMapping("/article_image/images")
	public String addArticleImage(//
			@RequestParam("id") final int articleId,//
			@RequestPart("image") final MultipartFile file) {
		return addArticleImage(articleId, file);
	}

    @GetMapping("/article_image/images")
    @ResponseBody
    public byte[] getArticleImage(@PathVariable("id") Integer articleImageId) {
        // 根據文章圖片ID取得相應的圖片資料
        ArticleImage articleImage = articleImageService.getArticleImageById(articleImageId);
        // 回傳圖片資料
        return articleImage.getImage();
    }

    @PutMapping("/article_image/images")
    public ResponseEntity<ArticleImage> updateArticleImage(@PathVariable("id") Integer id, @RequestBody ArticleImage articleImage) {
        ArticleImage updatedArticleImage = articleImageService.updateArticleImage(id, articleImage);
        if (updatedArticleImage != null) {
            return new ResponseEntity<>(updatedArticleImage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/article_image/images")
    public ResponseEntity<Void> deleteArticleImage(@PathVariable("id") Integer id) {
        boolean deleted = articleImageService.deleteArticleImage(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
