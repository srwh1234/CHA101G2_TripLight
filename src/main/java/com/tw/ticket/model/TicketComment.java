package com.tw.ticket.model;

import java.sql.Timestamp;

import com.tw.member.model.Member;

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
public class TicketComment {

	public static int STATUS_HIDE = 0;
	public static int STATUS_SHOW = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 流水號

	private Integer ticketId;// 被評論的票券編號

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;// 評論者的會員編號

	private String comment;// 評論內容

	private Integer status;// 狀態 (0:不顯示 1:顯示)

	private Timestamp postTime;// 發表時間

	private Integer editCount = 0;// 編輯次數

	private Timestamp lastEditTime;// 最後編輯時間

	private Integer rating;// 評價分數
}
