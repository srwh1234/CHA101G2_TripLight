package com.tw.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {

	private static final String SESSION_ID_COOKIE_NAME = "memberId";

	private final MemberRepository memberRepository;

	@Autowired
	public LoginController(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@PostMapping("/login")
	public String login(@RequestParam final String username, @RequestParam final String password, final HttpSession session) {
		final Member user = memberRepository.findMemberByMemberEmail(username);
		if (user != null) {
			session.setAttribute("user", user);
			return "redirect:/home";
		}
		return "login";

	}

}
