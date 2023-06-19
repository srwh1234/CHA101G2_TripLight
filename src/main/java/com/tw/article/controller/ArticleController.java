package com.tw.article.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;
import com.tw.article.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {
	
	//private final ArticleRepository articleRepository;
	 private final ArticleService articleService; 
    
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    
    // 定義GET請求處理方法：單一
    @GetMapping("/{articleId}")
    public Article getArticle(@PathVariable Integer articleId) {
        return articleService.findById(articleId);
//                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
    }
    
    // 定義GET請求處理方法：全部
    public List<Article> getAllArticles(){
    	return articleService.getAllArticles();
    }
    
    // 定義POST請求處理方法
    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        
    	// 在此處對文章進行保存、驗證或其他邏輯處理?
    	
        return articleService.save(article);
    }
    
    // 定義PUT請求處理方法
    @PutMapping("/{articleId}")
    public Article updateArticle(@PathVariable Integer articleId, @RequestBody Article updatedArticle) {
        Article article = articleService.findById(articleId);
//                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
        return articleService.save(article);
    }
    
    // 定義DELETE請求處理方法
    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Integer articleId) {
        Article article = articleService.findById(articleId);
//                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));
        articleService.deleteArticle(article);
    }
    
 //   private ArticleRepository dao;

//	public void ArticleService() {
//		dao = new ArticleServiceImpl();
//	}

	// 依照文章編號搜尋
	public Article getOneArticle(Integer articleId) {
		//return dao.findByPrimaryKey(articleId);
		//XXX 
				return null;
	}

	// 熱門文章
	public List<Article> findTopUserAllPost() {
		//return dao.findByTopArticle();
		//XXX 
				return null;
	}

	// 會員個人已發表文章列表
		public List<Article> getAllArticleStatus() {
			//XXX 
//			return dao.getAllarticleStatus();
			return null;
		}
		
	// 會員個人已發表文章列表 
	public List<Article> findMyPost(Integer memberId) {
//		return dao.findByMemberId(memberId);
		//XXX 
		return null;
	}

	// 依照文章類別搜尋
	public List<Article> findPostType(Integer articleTypeId) {
		//return dao.findByPostType(articleTypeId);
		//XXX 
		return null;
	}

	// 依照關鍵字類別搜尋
	public List<Article> searchByKeyword(String keyword) {
		//return dao.searchByKeyword(keyword);
		//XXX 
		return null;
	}

	// 綜合查詢
	public List<Article> getAllPowerSearch(Map<String, String[]> map) {
		//XXX 
		//return dao.getPowerAll(map);
		return null;
	}

	// 查全部
//	public List<Article> getAll() {
//		return dao.getAll();
//	}

	// 新增文章
	public Article createArticle(Integer memberId, Integer articleTypeId, String articleTitle, String articlePostContent, byte[] image) {

		Article article = new Article();
		article.setMemberId(memberId);
		article.setArticleTypeId(articleTypeId);
		article.setArticleTitle(articleTitle);
		article.setArticlePostContent(articlePostContent);
		article.setArticleImage(image);
		//XXX 
		//dao.createArticle(article);
		return article;
	}

	// 修改文章
	public Article updateArticle(Integer articleId, Integer memberId, Integer articleTypeId, String articleTitle, String articlePostContent,
			Integer articleStatus, Integer articleViews, Integer articleLikesCount) {
		Article article = new Article();
		article.setArticleId(articleId);
		article.setMemberId(memberId);
		article.setArticleTypeId(articleTypeId);
		article.setArticleTitle(articleTitle);
		article.setArticlePostContent(articlePostContent);
		article.setArticleStatus(articleStatus);
		article.setArticleViews(articleViews);
		article.setArticleLikesCount(articleLikesCount);
		//XXX 
		//dao.updateArticle(article);
		return article;
	}

//	public void updateArticle(Article article) {
//		dao.update(article);
//	}

	// 修改文章狀態
	public Article setArticleStatus(Integer articleId, Integer articleTypeId, Integer articleStatus) {

		Article article = new Article();
		article.setArticleId(articleId);
		article.setArticleTypeId(articleTypeId);
		article.setArticleStatus(articleStatus);
		//XXX 
		//dao.updateArticleStatus(article);
		return null;
	}

	// 觀看次數
	public void ArticleViews(Integer articleId) {
		//XXX 
		//dao.articleViews(articleId);
	}

	// 刪除文章 
//	public void deleteArticle(Integer artId) {
//		dao.delete(artId);
//	}

	// 單一文章找用戶 
	public Article findPostMan(Integer memberId) {
		//return dao.findPostMan(memberId);
		//XXX 
		return null;
	}

	public ArticleService getArticleService() {
		return articleService;
	}
}
