package com.tw.article.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.article.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository <Article, Integer> {
	//取得基本CRUD之增、刪、改、查的方法，也可延伸再定義自用的方法
		
		//尋找會員編號
	  //  public Article findPostMan (Integer memberId);
	
	    // 以文章編號搜尋
//		public Article findByPrimaryKey(Integer articleId);
		
	    // 會員個人已發表文章列表
//		public List<Article> findByMemberId(Integer memberId);
		
		// 用文章標題&分類收尋
	//	public List<Article> findByPostType(Integer articleTypeId);

		// 用關鍵字搜尋
	//	public List<Article> searchByKeyword(String keyword);
		
		//所有狀態為顯示的文章 狀態0為顯示
//		public List<Article> getAllarticleStatus();
		
		//單一文章搜尋
		//public List<Article> findOnePost(Integer articleId);
		
		// 熱門文章
	//	public List<Article> findByTopArticle( );
		
		// 搜全部
//		public List<Article> getAll();
		
		// 萬用複合查詢(傳入參數型態Map)(回傳 List)
	//	public List<Article> getPowerAll(Map<String, String[]> map);
		
		// 新增文章 
//		public void createArticle(Article article);

		// 一般會員修改文章
//		public void updateArticle(Article article);
		
	    //後台人員修改文章狀態、一般會員刪除文章
//		public void updateArticleStatus(Article article);
		
//		// 後台人員刪除文章
//		public void deleteArticle(Integer articleId);
		
		// 觀看次數
	//	public void articleViews(Integer articleId); 

		// 增加觀看次數
	//	public void updateArticleViews(Integer articleId);
		
		//備用方法
	//	public void deleteArticle(Article article);

}
