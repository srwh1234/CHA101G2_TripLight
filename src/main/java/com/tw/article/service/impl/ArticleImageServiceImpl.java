package com.tw.article.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tw.article.dao.ArticleImageRepository;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;
import com.tw.article.model.ArticleImage;
import com.tw.article.service.ArticleImageService;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;


@Service
public class ArticleImageServiceImpl implements ArticleImageService {
	
	public static String IMG_URL = "http://localhost:8080/article_image/";
	
	@Autowired
	private ArticleRepository repository;
	@Autowired
	private ArticleImageRepository articleImageRepository;

    @Autowired
    public void ArticleImageService(ArticleImageRepository articleImageRepository) {
        this.articleImageRepository = articleImageRepository;
    }
    
    @PostMapping
    public String addArticleImage(int articleimageId, MultipartFile file) {
    	String result = null;

		final Article article = repository.findById(articleimageId).orElse(null);

		if (article == null) {
			return result;
		}

		try {
		    byte[] array = file.getBytes();

			ArticleImage image = new ArticleImage();
			image.setArticleImageId(articleimageId);
			image.setImage(array);
			image.setUploadTime(new Timestamp(System.currentTimeMillis()));

			articleImageRepository.save(image);

			result = IMG_URL  + image.getArticleImageId();
		} catch (IOException e) {
//			log.error(e.getLocalizedMessage(), e);
		}

		return result;
	}
    
    @GetMapping("/article_image/images")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getArticleImage(@RequestBody Map<String, Integer> request) {
        Integer articleImageId = request.get("articleImageId");
        
        // 根據文章圖片ID從資料庫或其他位置取得相應的ArticleImage物件
        ArticleImage articleImage = ArticleImageService.getArticleImage(articleImageId);
        
        if (articleImage != null) {
            // 建立回應體資料
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("articleImageId", articleImage.getArticleImageId());
            responseBody.put("articleId", articleImage.getArticleId());
            responseBody.put("image", Base64.getEncoder().encodeToString(articleImage.getImage()));
            responseBody.put("uploadTime", articleImage.getUploadTime());

            // 建立HTTP響應物件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 設定響應狀態為200 OK
            HttpStatus status = HttpStatus.OK;

            // 建立ResponseEntity物件，將回應體資料、響應頭和狀態碼打包成一個響應物件返回
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(responseBody, headers, status);
            return responseEntity;
        } else {
            // 如果找不到相應的文章圖片，可以返回404 Not Found響應
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ArticleImage updateArticleImage(Integer id, ArticleImage articleImage) {
        Optional<ArticleImage> optionalArticleImage = articleImageRepository.findById(id);
        if (optionalArticleImage.isPresent()) {
            ArticleImage existingArticleImage = optionalArticleImage.get();
            existingArticleImage.setArticleId(articleImage.getArticleId());
            existingArticleImage.setImage(articleImage.getImage());
            existingArticleImage.setUploadTime(articleImage.getUploadTime());
            return articleImageRepository.save(existingArticleImage);
        } else {
            return null;
        }
    }

    public boolean deleteArticleImage(Integer id) {
        Optional<ArticleImage> optionalArticleImage = articleImageRepository.findById(id);
        if (optionalArticleImage.isPresent()) {
            articleImageRepository.delete(optionalArticleImage.get());
            return true;
        } else {
            return false;
        }
    }

	public ArticleImage getArticleImage(Integer articleImageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleImage updateArticleImage(int articleimageid, ArticleImage articleimage) {
		// TODO Auto-generated method stub
		return null;
	}
}

