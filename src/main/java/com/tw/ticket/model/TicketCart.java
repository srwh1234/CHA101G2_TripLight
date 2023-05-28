package com.tw.ticket.model;

import java.io.Serializable;

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
public class TicketCart implements Serializable {
	private static final long serialVersionUID = 1L;

	// 非必要的建構子 只是為了輸入方便
	public TicketCart(final Member member, final Ticket ticket, final Integer quantity) {
		this.key = new PrimaryKey(member, ticket);
		this.quantity = quantity;
	}

	@EmbeddedId
	private PrimaryKey key; // 複合主鍵的關係

	private Integer quantity;// 欲購買的數量

	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "member_id")
		private Member member;// 會員編號

		@ManyToOne
		@JoinColumn(name = "ticket_id")
		private Ticket ticket;// 欲購買的票券編號
	}
}
