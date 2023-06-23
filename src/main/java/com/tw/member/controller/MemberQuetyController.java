package com.tw.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.model.Member;
import com.tw.member.service.MemberQuetyService;

@RestController
public class MemberQuetyController {
	@Autowired
	private MemberQuetyService memberQuetyService;
	//取得所有會員資料
	@GetMapping("/members")
	public List<Member> getAllMembers() {
		List<Member> members = memberQuetyService.findAll();
		return members;
	}
	
	//停權
	@PostMapping("/suspension/{memberId}")
	public Member suspension(@PathVariable("memberId") int memberId,@RequestParam int memberStatus ) {
		System.out.println("find all control");
		return memberQuetyService.suspension(memberId, memberStatus);
	}  

	
}
