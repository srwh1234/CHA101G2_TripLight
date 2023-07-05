package com.tw.member.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Base64;

import jakarta.persistence.*;
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

	private String memberAccount = "";
//	private String memberAccount;	

	private String memberPassword;

	private Integer memberStatus;

	private String memberNameLast;

	private String memberNameFirst;

	private String memberPhone;

	private String memberEmail;

	private Date memberBirth;

	private Integer memberGender;

	@Lob
	@Column(name = "member_pic", columnDefinition = "BLOB")
	private byte[] memberPic;

	private String memberIdCard;

	private String memberCity;

	private String memberDist;

	private String memberAddress;

	private java.util.Date memberJoinTime;

	private Integer memberGrade;

	public Member(String memberNameLast, String memberNameFirst, String memberPhone){
		this.memberNameLast = memberNameLast;
		this.memberNameFirst = memberNameFirst;
		this.memberPhone = memberPhone;
	}

	public Member(String memberNameLast, String memberNameFirst, String memberPhone, String memberEmail){
		this.memberFullName = memberNameLast + memberNameFirst;
		this.memberPhone = memberPhone;
		this.memberEmail = memberEmail;
	}

	public Member(String memberFullName, byte[] memberPicArg){

			System.out.println("test in member"+ memberFullName);
			this.memberFullName = memberFullName;
			memberPic = memberPicArg;

	}

	@Transient
	private String memberFullName;

	@Transient
	private String memberPicBase64;
}
