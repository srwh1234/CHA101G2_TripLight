package com.tw.ticket.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.tw.member.model.Member;

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
public class TicketFavorite {

	@EmbeddedId
	private PrimaryKey key; // 複合主鍵的關係

	private Timestamp addTime;// 加入日期

	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "member_id")
		private Member member;// 收藏者的會員編號

		@ManyToOne
		@JoinColumn(name = "ticket_id")
		private Ticket ticket;// 票券編號

	}
}
