package com.tw.article.model;

import java.io.Serializable;
import com.tw.member.model.Member;
import com.tw.ticket.model.Ticket;
import com.tw.article.model.ArticleFavorite;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFavorite {

	@EmbeddedId
	private PrimaryKey key; // 複合主鍵的關係

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryKey implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;// 收藏者的會員編號

	@ManyToOne
	@JoinColumn(name = "article_id")
	private Article article;// 票券編號
	}
}