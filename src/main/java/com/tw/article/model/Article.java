package com.tw.article.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements java.io.Serializable{
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer articleId;				//文章編號
		
		private Integer memberId;				//發文者會員編號
		
		private Integer articleTypeId;			//文章類型編號
		
		private String articleTitle;			//文章標題
		
		private String articlePostContent;		//文章內容
		
		private Timestamp articlePostTime;		//文章發表時間
		
		private Integer articleStatus;			//文章狀態
		
		private Integer articleViews;			//文章瀏覽次數
		
		private byte[] articleImage;			//文章圖片，備用
		
		private Integer articleLikesCount;      //文章好評，備用
		
		
		
//		public com.Users.model.UsersVO getUsersVO() {
//			com.Users.model.UsersJDBCDAO userDAO = new com.Users.model.UsersJDBCDAO();
//			com.Users.model.UsersVO usersVO = userDAO.findByPrimaryKey(userId);
//			return usersVO;
//		}
//		
//		public com.article_report.model.ArticleReportVO getArticleReportVO() {
//			com.article_report.model.ArticleReportJDBCDAO articleReportJDBCDAO = new com.article_report.model.ArticleReportJDBCDAO();
//			com.article_report.model.ArticleReportVO articleReportVO = articleReportJDBCDAO.findByPrimaryKey(artId);
//			return articleReportVO;
//			
//		for join artpic from 多張照片用 
//		public com.article_pic.model.ArticlePicVO getArtPicVO() { // 有個方法叫getArtPicVO
//			 com.article_pic.model.ArticlePicService artpicSvc = new com.article_pic.model.ArticlePicService(); //找到Service
//			 com.article_pic.model.ArticlePicVO artPicVO = artpicSvc.getArtPic(artPic);
//			 return artPicVO;			
//		}
}
