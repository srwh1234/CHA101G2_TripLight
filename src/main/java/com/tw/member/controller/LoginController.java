package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/login")
	public Integer login(@RequestParam String email, @RequestParam String password, HttpSession session) {

		Integer result = loginService.login(email, password);
		//沒有此帳號
		if (result == null) {
			return 0;
		}else if(result == -1) {   //停權
			return -1;
		}
		else {
			// 設置Session
			Member member = loginService.getMember(email);
			session.setAttribute("member",member);
			return result;
		}
	}

	@GetMapping("/getName/{id}")
	public String getName(@PathVariable("id") int id) {
		String name = loginService.getName(id);
		return name;
	}

//============================================================
	@PostMapping("/register")
	public boolean register(@RequestParam String email, @RequestParam String password, @RequestParam String account) {

		boolean registerStatus = loginService.register(email, password, account);
		if (registerStatus) {
			//System.out.println("success");
			return true;
		} else {
			//System.out.println("fail");
//			return "redirect:/front-end/index.html";
			return false;
		}
	}
//============================================================

	@GetMapping("/logout")
	public String logout(HttpSession session, SessionStatus sessionStatus) {

		if (session.getAttribute("member") != null) {
			session.removeAttribute("member");
			sessionStatus.setComplete();
		}
		return "redirect:/front-end/index.html";
	}

}
