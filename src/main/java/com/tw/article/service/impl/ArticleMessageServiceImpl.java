package com.tw.article.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tw.article.dao.ArticleMessageRepository;
import com.tw.article.model.ArticleMessage;
import com.tw.article.service.ArticleMessageService;

@Service
public class ArticleMessageServiceImpl implements ArticleMessageService {
	
	private final ArticleMessageRepository articleMessageRepository;
	private Integer articleMessageId;

	@Autowired
	public ArticleMessageServiceImpl(ArticleMessageRepository articleMessageRepository) {
		 this.articleMessageRepository = articleMessageRepository;
	}

	@Override
	public ArticleMessage addArticleMessage(ArticleMessage articleMessage) {
		if (isExceedingCharacterLimit(articleMessage.getMessagePostcontent())) {
            throw new IllegalArgumentException("留言字數超過限制");
        }
        return articleMessageRepository.save(articleMessage);
    }

	@Override
	public ArticleMessage updateArticleMessage(ArticleMessage articleMessage) {
		if (isExceedingCharacterLimit(articleMessage.getMessagePostcontent())) {
            throw new IllegalArgumentException("留言字數超過限制");
        }
        // 检查文章消息是否存在
        if (articleMessageRepository.existsById(articleMessage.getArticleMessageId())) {
            return articleMessageRepository.save(articleMessage);
        } else {
            throw new IllegalArgumentException("Article message does not exist with ID: " + articleMessage.getArticleMessageId());
        }
	}

	public void deleteArticleMessage(Integer articleMessageId) {
		articleMessageRepository.deleteById(articleMessageId);
	}

	@Override
	public List<ArticleMessage> getAllArticleMessages() {
		return articleMessageRepository.findAll();
	}

	@Override
	public String getArticleMessageById(Integer articleIdMessageId) {
		Optional<ArticleMessage> optionalArticleMessage = articleMessageRepository.findById(articleMessageId);
		if (optionalArticleMessage.isPresent()) {
	        ArticleMessage articleMessage = optionalArticleMessage.get();
	        return articleMessage.getMessagePostcontent();
	    } else {
	        throw new IllegalArgumentException("Article message not found with ID: " + articleMessageId);
	    }
	
	}
	
	private boolean isExceedingCharacterLimit(String ArticleMessage) {
        return ArticleMessage.length() > 2500 ;
    }
	
}
