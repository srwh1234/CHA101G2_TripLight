package com.tw.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

@Service
public class LoginService {
	@Autowired
	private MemberRepository memberRepository;

	
	public boolean register(String email, String password, String account) {
        Member existingMember = memberRepository.findByMemberEmail(email);
        if (existingMember != null) {
            return false; // 用戶名已存在
        }
        Member member = new Member();
        member.setMemberEmail(email);
        member.setMemberPassword(password);
        member.setMemberAccount(account);
        memberRepository.save(member);
        
        return true; // 註冊成功
    }

    public Member login(String email, String password) {
        Member member =  memberRepository.findByMemberEmail(email);
        if (member != null && password.equals(member.getMemberPassword())) {
            return member; // 登入成功
        }
        return null; // 登入失敗
    }
    
    public Integer getMemberId(String email) {
    	Member member = memberRepository.findByMemberEmail(email);
    	if(member != null) {
    		return member.getMemberId();    		
    	}
    	return null;
    }

}
