package com.tw.article.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtycleType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleTypeId; // 文章類型編號
	
	private String articleTypeName; // 文章類型
	
	private String articleTypeDescribe; // 文章類型描述

}
