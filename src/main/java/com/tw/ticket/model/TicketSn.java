package com.tw.ticket.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSn implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int NOT_USED = 0;
	public static int IN_USED = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticketSnId;// 票券序號編號

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;// 票券編號

	private String serialNumber;// 虛擬序號

	private Integer status;// 狀態 (0:未使用 1:已使用)

}
