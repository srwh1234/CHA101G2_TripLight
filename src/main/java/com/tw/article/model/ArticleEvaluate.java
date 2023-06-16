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
public class ArticleEvaluate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleId; // 文章編號
	
	private Integer memberId; // 評價者會員編號
	
	private Integer evaluateLike; // 評價: 好評
	
	private Integer evaluateDislike; // 評價: 負評
	
	private Timestamp evaluatePostTime; // 發表時間
	
}
