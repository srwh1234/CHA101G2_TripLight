package com.tw.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

@Service
public class MemberQuetyService {
	@Autowired
	private MemberRepository memberRepository;
	//顯示資料
	public List<Member> findAll(){
		List<Member> members = memberRepository.findAll();
		return members;
	}
	//停權
	public Member suspension(int memberId, int memberStatus) {
		Member existingMember = memberRepository.findByMemberId(memberId);
		if(existingMember != null) {
			existingMember.setMemberStatus(memberStatus);
			//System.out.println("find all service");
			return memberRepository.save(existingMember);
		}
		return null;
	}


}
