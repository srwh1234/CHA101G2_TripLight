package com.tw.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tw.article.model.ArticleEvaluate;

public interface ArticleEvaluateRepository extends JpaRepository<ArticleEvaluate, Integer>{
	//取得基本CRUD之增、刪、改、查的方法，也可延伸再定義自用的方法
}
