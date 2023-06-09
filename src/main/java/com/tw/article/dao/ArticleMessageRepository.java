package com.tw.article.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.article.controller.ArticleMessageController.DataMessage;
import com.tw.article.model.ArticleMessage;

public interface ArticleMessageRepository extends JpaRepository <ArticleMessage, Integer>{
	//取得基本CRUD之增、刪、改、查的方法，也可延伸再定義自用的方法
	
	List<ArticleMessage> findByArticleId(int articleId);

}
