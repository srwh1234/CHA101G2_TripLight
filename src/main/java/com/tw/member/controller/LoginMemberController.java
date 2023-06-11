package com.tw.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.model.Member;
import com.tw.member.service.LoginMemberService;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginMemberController {
	@Autowired
	private LoginMemberService memberService;

//	@PostMapping("/member/{id}")
//	public Member updateMember(@RequestBody Member member) {
//		return memberService.updateMember(int id, member);
//	}

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
//	// 查詢
//    @GetMapping("/member")
//    public List<Member> getMember() {
//        return memberService.getAllMembers();
//    }
//	// 新增
//    @PostMapping("/member")
//    public Member processMember(@RequestBody Member member) {
//        return memberService.createMember(member);
//    }

    // update
//    @PostMapping("/member/{id}")
//    public Member updateMember(@PathVariable int id,@RequestBody Member data) {
//    	String message = "Hello, " + data.getMemberNameFirst() ;
//        return memberService.updateMember(id, data);
//    }
//    @PostMapping("/member")
//    public String createMember(@RequestBody Member data) {
//        // 在這裡處理接收到的資料物件
//        // 可以根據需要進行適當的操作
//        return message;
    //}
    
//    @PostMapping("/member/{memberId}")
//    public ResponseEntity<Boolean> processResultData(@RequestParam("resultData") String resultData, @RequestParam("resultUrl") String resultUrl, @PathVariable("memberId") String memberId, HttpSession session) {
//        try {
//            var user = (Member)session.getAttribute("user");
//            boolean saved = aiService.save(resultData, resultUrl, memberId,user.getMemberId());
//            return ResponseEntity.ok(saved);
//        } catch (Exception e) {
//            // 處理異常狀況
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
//        }
//    }
}
