package com.tw.ticket.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ticket_image")
public class TicketImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ticket_id")
	private int ticketId;

	@Lob
	@Column(name = "image")
	private byte[] image;

	@Column(name = "upload_time")
	private Timestamp uploadTime;


}
