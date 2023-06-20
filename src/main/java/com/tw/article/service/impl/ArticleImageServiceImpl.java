package com.tw.article.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tw.article.dao.ArticleImageRepository;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;
import com.tw.article.model.ArticleImage;


@Service
public class ArticleImageServiceImpl implements com.tw.article.service.ArticleImageService {
	
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

    public ArticleImage getArticleImageById(Integer id) {
        Optional<ArticleImage> optionalArticleImage = articleImageRepository.findById(id);
        return optionalArticleImage.orElse(null);
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

	@Override
	public List<ArticleImage> getAllArticles() {
		// TODO Auto-generated method stub
		return null;
	}
}

