package com.tw.member.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.model.Member;
import com.tw.member.service.LoginMemberService;

import lombok.Data;

@RestController
public class LoginMemberController {
	@Autowired
	private LoginMemberService memberService;

	@GetMapping("/memberdetail/{id}")
	public MemberDetail detail(@PathVariable("id") int id) {
		return memberService.getItem(id);
	}

	@Data
	public static class MemberDetail {
		public MemberDetail(Member member) {
			this.memberNameLast = member.getMemberNameLast();
			this.memberNameFirst = member.getMemberNameFirst();
			this.memberIdCard = member.getMemberIdCard();
			this.memberBirth = member.getMemberBirth();
			this.memberPhone = member.getMemberPhone();
			this.memberGender = member.getMemberGender();
			this.memberCity = member.getMemberCity();
			this.memberDist = member.getMemberDist();
			this.memberAddress = member.getMemberAddress();
			this.memberEmail = member.getMemberEmail();
		}

		private String memberNameLast;

		private String memberNameFirst;

		private String memberPhone;

		private String memberEmail;

		private Date memberBirth;

		private Integer memberGender;

		private String memberIdCard;

		private String memberCity;

		private String memberDist;

		private String memberAddress;

	}

	// update
	@PostMapping("/member/{id}")
	public Member update(@PathVariable("id") int id, @RequestBody Member data) {
		return memberService.updateMember(id, data);
	}

	// get old password
	@PostMapping("/pwd/{id}")
	public boolean getOldPwd(@PathVariable("id") int id, @RequestParam("memberPassword") String password) {
		boolean oldPwdStatus = memberService.getPwd(id, password);
		if (oldPwdStatus) {
			return true;
		}
		return false;
	}

	// change password
	@PostMapping("/changePwd")
	public void changePwd(@RequestParam int id, @RequestParam String password) {
		System.out.println("changePwd controller");
		memberService.changePwd(id, password);
	}

	// 存圖片
	@PostMapping("/uploadImage/{id}")
	public void uploadImage(@PathVariable("id") int id, @RequestParam byte[] memberPic) {
		memberService.saveImage(id, memberPic);
		System.out.println("save controller");
	}
	//顯示圖片
	@GetMapping("/getPic/{id}")
	public byte[] getPic(@PathVariable("id")int id) {
		return memberService.getPic(id);
	}
//	public MemberDetail detail(@PathVariable("id") int id) {
//		return memberService.getItem(id);
//	}
	

}
