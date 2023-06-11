package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.model.Member;
import com.tw.member.service.LoginMemberService;

@RestController
public class LoginMemberController {
	@Autowired
	private LoginMemberService memberService;

//	@PostMapping("/member/{id}")
//	public Member updateMember(@RequestBody Member member) {
//		return memberService.updateMember(int id, member);
//	}
	
	 @PostMapping("/processResultData/{memberId}")
	    public String processResultData(@RequestParam("text") String resultData, @RequestParam("url") String resultUrl, @PathVariable("memberId") String memberId) {
	        int aiFavoriteId = memberService.save(resultData, resultUrl, memberId);
	        memberService.saveLocation(memberId, aiFavoriteId);
	        return "success";
	    }
//	@PostMapping("/saveMember")
//	public ResponseEntity<String> saveMember (@RequestBody Member member){
//		Member savedMember = memberService.saveMember(member);
//		return ResponseEntity.ok("User data saved successlly");
//	}
//	@GetMapping("/getMember/{memberId}")
//	public ResponseEntity<Member> getMember(@PathVariable int memberId){
//		Member member = memberService.getMemberById(memberId);
//		return ResponseEntity.ok(member);
//		}
//
}
