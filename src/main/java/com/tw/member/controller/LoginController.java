package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.member.service.LoginService;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
	private final MemberRepository memberRepository;

	@Autowired
	public LoginController(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	@Autowired
	public LoginService loginService;

//	@PostMapping("/login")
//	public String login(@RequestParam final String email, @RequestParam final String password, final HttpSession session) {
//		final Member member = memberRepository.findMemberByMemberEmail(email);
//		if (member != null) {
//			session.setAttribute("member", member);
//			return "redirect:/home";
//		}
//		return "login";
//	}

//	@PostMapping("/login")
//	public String login (@RequestBody String email, @RequestBody String password, HttpSession session) {
//		if(email == login.)
//		return null;
//	}
	@PostMapping("/login")
	public boolean login(@RequestParam String email, @RequestParam String password, HttpSession session) {
		
		Member result = loginService.login(email, password);
		if(result == null) {
			System.out.println("沒有此帳號");;
			return false;
		}else {
			System.out.println("後端成功");
			// 設置Session
			session.setAttribute("member", result);
			return true;			
		}
	}

	@PostMapping("/register")
	public boolean register(@RequestParam String email, @RequestParam String password, @RequestParam String account) {
		
		boolean registerStatus = loginService.register(email, password, account);
		if(registerStatus) {
			System.out.println("success");
			return true;
		}else {
			System.out.println("fail");
//			return "redirect:/front-end/index.html";
			return false;
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session, SessionStatus sessionStatus) {
		
		if(session.getAttribute("member") != null){
			session.removeAttribute("member");
			sessionStatus.setComplete();
		}		
		return "redirect:/front-end/index.html";
	}

}
