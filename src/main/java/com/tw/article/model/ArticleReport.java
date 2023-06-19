package com.tw.article.model;

import java.sql.Timestamp;

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
public class ArticleReport implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleReportId;	    //文章檢舉編號
	
	private Integer memberId;				//檢舉會員編號
	
	private Integer articleId;			    //文章編號
	
	private Integer employeeId;				//處理員工
	
	private Timestamp articleReportTime;	//提出檢舉時間
	
	private String articleReportReason;		//檢舉原因
	
	private Integer articleReportStatus;	//檢舉狀態
	
}
