package com.tw.ticket.model;

import java.sql.Date;
import java.util.List;

import org.springframework.data.rest.core.annotation.RestResource;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer promotionId;

	private String promotionTitle;

	private Date startDate;

	private Date endDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "promotionId")
	@RestResource(exported = false) // 不導出url
	private List<PromotionDetail> promotionDetails;

}
