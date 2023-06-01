package com.tw.ticket.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
	public TicketCart(final Integer memberId, final Integer ticketId, final Integer quantity) {
		this.key = new PrimaryKey(memberId, ticketId);
		this.quantity = quantity;
	}

	@EmbeddedId
	private PrimaryKey key; // 複合主鍵的關係

	private Integer quantity;// 欲購買的數量

	// 加上指定的數量
	public void addQuantity(final int i) {
		quantity += i;
	}

	// 獲得票券編號
	public long getTicketId() {
		return key.getTicketId();
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer memberId;// 會員編號

		private Integer ticketId;// 欲購買的票券編號
	}
}
