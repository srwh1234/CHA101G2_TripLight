package com.tw.ticket.model;


import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.Data;


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
