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
public class ArticleMessage implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleMessageId;		//文章留言編號
	
	private Integer articleId;				//文章編號
	
	private Integer memberId;				//留言者會員編號
	
	private String messagePostcontent;		//留言內容
	
	private Timestamp messagePostTime;		//發表時間
	
	private Integer messagePreviousId;		//上一層留言編號
	
	private Integer messageStatus;			//留言狀態
}
