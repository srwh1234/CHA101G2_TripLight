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
import com.tw.ticket.controller.TicketDetailController.DetailResponse;

import lombok.Data;

@RestController
public class LoginMemberController {
	@Autowired
	private LoginMemberService memberService;

//	@GetMapping("/getMember/{Id}")
//	public ResponseEntity<Member> getMemberById(@PathVariable int Id, @RequestBody Member data) {
//		Member member = memberService.getMemberById(Id, data);
//		return ResponseEntity.ok(member);
//	}
	@GetMapping("/memberdetail/{id}")
	public MemberDetail detail(@PathVariable("id") int id) {
		System.out.println(id);
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
//	 @GetMapping("/user")
//	    public User getUser() {
//	        User user = // 從資料庫或其他來源取得使用者資料
//	        return user;
//	    }

	// update
	@PostMapping("/member/{id}")
	public Member update(@PathVariable("id") int id, @RequestBody Member data) {
		return memberService.updateMember(id, data);
	}

}
