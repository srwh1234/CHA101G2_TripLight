package com.tw.ticket.model;

import jakarta.persistence.Entity;
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
public class TicketSn {

	@Id
	private Integer ticketSnId;// 票券序號編號

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;// 票券編號

	private String serialNumber;// 虛擬序號

	private Integer status;// 狀態 (0:未使用 1:已使用)

}
