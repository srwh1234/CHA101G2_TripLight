package com.tw.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.dao.MemberRepository;

@Service
public class LoginService {
	@Autowired
	private MemberRepository memberRepository;
}
