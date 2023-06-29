package com.tw.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.article.model.ArticleFavorite;
import com.tw.article.model.ArticleFavorite.PrimaryKey;

@Repository
public interface ArticleFavoriteRepository extends JpaRepository <ArticleFavorite, PrimaryKey> {
	//取得基本CRUD之增、刪、改、查的方法，也可延伸再定義自用的方法
//	public List<ArticleFavorite> findByKeyMemberId(int memberId);
//
////	public void deleteByKeyArticle(PrimaryKey key);
//
//	public void deleteById(int memberId);
}
