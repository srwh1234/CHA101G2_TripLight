package com.tw.ticket.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetail {

	@EmbeddedId
	private PrimaryKey key;

	private int promotionPrice;

	@OneToOne
	@JoinColumn(name = "ticketId", insertable = false, updatable = false)
	private Ticket ticket;

	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer promotionId;
		private Integer ticketId;
	}
}
