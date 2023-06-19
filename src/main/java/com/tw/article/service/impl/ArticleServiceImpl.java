package com.tw.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tw.article.dao.ArticleImageRepository;
import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;
import com.tw.article.service.ArticleService;
import com.tw.ticket.model.TicketImage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article createArticle(Article article) {
    	article.getArticleTypeId();
    	String articleTitle = article.getArticleTitle();
    	if (articleTitle == null || articleTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("標題不能為空");
        } 
        String articlePostContent = article.getArticlePostContent();
        if (articlePostContent == null || articlePostContent.trim().isEmpty()) {
            throw new IllegalArgumentException("內容不能為空");
        } 
			return articleRepository.save(article);
		
    }
    @Override
    public Article updateArticle(Article article) {
    	article.getArticleTypeId();
    	String articleTitle = article.getArticleTitle();
    	if (articleTitle == null || articleTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("標題不能為空");
        } 
        String articlePostContent = article.getArticlePostContent();
        if (articlePostContent == null || articlePostContent.trim().isEmpty()) {
            throw new IllegalArgumentException("內容不能為空");
        } 
			return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Article article) {
       // articleRepository.deleteArticle(article);;
    }

    @Override
    public Optional<Article> getArticle(Integer articleId) {
        return articleRepository.findById(articleId);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article findById(Integer articleId) {
        // 实现通过文章ID查找文章的逻辑
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        return optionalArticle.orElse(null);
    }
    
    @Override
	public Article save(Article article) {
    	return articleRepository.save(article);
    }
    
    
//    @Override
//	public Article findByPrimaryKey(Integer articleId) {
//
//		Article article = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			Class.forName(driver); // 找到驅動程式
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//
//			pstmt.setInt(1, articleId);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				Article article = new Article();
//				article.setArticleId(rs.getInt("ARTICLE_ID"));
//				article.setMemberId(rs.getInt("MEMBER_ID"));
//				article.setArticleTypeId(rs.getInt("ARTICLE_TYPE_ID"));
//				article.setArticleTitle(rs.getString("ARTICLE_TITLE"));
//				article.setArticlePostContent(rs.getString("ARTRTICLE_POST_CONTENT"));
//				article.setArticlePostTime(rs.getObject("ARTICLE_POST_TIME", Timestamp.class));
//				article.setArticleStatus(rs.getInt("ARTICLE_STATUS"));
//				article.setArticleViews(rs.getInt("ARTICLE_VIEWS"));
//				article.setArticleImage(rs.getBytes("Article_Image"));
//				article.setArticleLikesCount(rs.getInt("ARTICLE_LIKES_COUNT"));
//			}
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return article;
//	}
    
}