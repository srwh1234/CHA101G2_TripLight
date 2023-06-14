package com.tw.article.service;

import java.util.List;
import com.tw.article.model.ArticleMessage;

public interface ArticleMessageService {
	
	//新增留言
    ArticleMessage addArticleMessage(ArticleMessage articleMessage);
    //修改留言
    ArticleMessage updateArticleMessage(ArticleMessage articleMessage);
    //刪除留言，應該用不到，改定義為隱藏
    void deleteArticleMessage(Integer articleIdMessageId);
    //查詢留言：單一
    String getArticleMessageById(Integer articleMessageId);
    //查詢文章：全部
    List<ArticleMessage> getAllArticleMessages();
}
