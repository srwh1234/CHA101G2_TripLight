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
	
//	public Member login(String email) {
//		
//		Member data = memberRepository.findByMemberEmail(email);
//		if(data == null) {
//			System.out.println("無此帳號");
//			return null;
//		}else {
//			System.out.println("成功登入");
//			Member member = new Member();
//			member.setMemberEmail(data.getMemberEmail());
//			member.setMemberPassword(data.getMemberPassword());
//			return member;
//			}
//		if(data != null) {
//			if(.equals(data.getMemberPassword())) return null;
//			
//		}
		
		// 取得對應Member 資料
//		Member member = memberService.findMemberByMa_id(data.getId());
//		if(member == null) return null;
//		
//		// 組合資料為MemberAccountVO
//		MemberAccountVO memberAccountVO = new MemberAccountVO();
//		memberAccountVO.setUsername(memberAccount.getUsername());
//		memberAccountVO.setName(member.getName());
//		return memberAccountVO;
//	}




}
