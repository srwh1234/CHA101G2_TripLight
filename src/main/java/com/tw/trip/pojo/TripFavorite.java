package com.tw.trip.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class TripFavorite implements Serializable{


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PrimaryKey2 key; // 複合主鍵的關係

	private Timestamp addTime;// 加入日期

	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrimaryKey2 implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer memberId;// 收藏者的會員編號

		@ManyToOne
		@JoinColumn(name = "trip_id")
		private Trip trip;// 旅遊團編號

	}
}
