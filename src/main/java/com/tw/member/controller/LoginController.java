package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
	private final MemberRepository memberRepository;

	@Autowired
	public LoginController(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

//	@PostMapping("/login")
//	public String login(@RequestParam final String email, @RequestParam final String password, final HttpSession session) {
//		final Member member = memberRepository.findMemberByMemberEmail(email);
//		if (member != null) {
//			session.setAttribute("member", member);
//			return "redirect:/home";
//		}
//		return "login";
//	}
//
//	@PostMapping("/register")
//	public Boolean register(@RequestParam final String account, @RequestParam final String email, @RequestParam final String password) {
//		try {
//			// var member = new Member();
//			// member.setMemberAccount(account);
//			// member.setMemberEmail(email);
//			// member.setMemberPassword(password);
//			// memberRepository.save(member);
//			// System.out.println("儲存成功：" + member);
//			return true;
//		} catch (final Exception e) {
//			return false;
//		}
//	}

	@GetMapping("/logout")
	public String logout(HttpSession session, SessionStatus sessionStatus) {
		
		if(session.getAttribute("member") != null){
			session.removeAttribute("member");
			sessionStatus.setComplete();
		}		
		return "redirect:/front-end/index.html";
	}

}
