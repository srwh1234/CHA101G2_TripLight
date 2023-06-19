package com.tw.article.service;

import com.tw.article.model.ArticleReport;

public interface ArticleReportSercive {
	    
	    ArticleReport getArticleReportById(Integer id);

	    ArticleReport createArticleReport(ArticleReport articleReport);

	    ArticleReport updateArticleReport(Integer id, ArticleReport articleReport);

	    boolean deleteArticleReport(Integer id);
	}
	

