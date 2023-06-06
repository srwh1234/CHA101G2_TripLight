package com.tw.member.controller;


import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final String SESSION_ID_COOKIE_NAME = "memberId";


    private final MemberRepository memberRepository;

    @Autowired
    public LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Member user = memberRepository.findMemberByMemberEmail(username);
        if(user != null) {
            session.setAttribute("user", user);
            return "redirect:/home";
        } else {
            return "login";
        }
    }




}

