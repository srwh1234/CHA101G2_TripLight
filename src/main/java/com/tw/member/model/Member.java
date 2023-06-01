package com.tw.member.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;

	private String memberAccount;

	private String memberPassword;

	private Integer memberStatus;

	private String memberNameLast;

	private String memberNameFirst;

	private String memberPhone;

	private String memberEmail;

	private Date memberBirth;

	private Integer memberGender;

	@Lob
	private byte[] memberPic;

	private String memberIdCard;

	private String memberCity;

	private String memberDist;

	private String memberAddress;

	private Date memberJoinTime;

	private Integer memberGrade;
}
