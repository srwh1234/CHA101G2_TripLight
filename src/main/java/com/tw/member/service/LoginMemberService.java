package com.tw.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

@Service
public class LoginMemberService {
	@Autowired
	private  MemberRepository memberRepository;

//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	public Member updateMember(int id, Member member) {
		Member existingMember = memberRepository.findByMemberId(id);
		if (existingMember != null) {
			existingMember.setMemberNameLast(member.getMemberNameLast());
			existingMember.setMemberNameFirst(member.getMemberNameFirst());
			existingMember.setMemberIdCard(member.getMemberIdCard());
			existingMember.setMemberBirth(member.getMemberBirth());
			existingMember.setMemberPhone(member.getMemberPhone());
			existingMember.setMemberGender(member.getMemberGender());
			existingMember.setMemberCity(member.getMemberCity());
			existingMember.setMemberDist(member.getMemberDist());
			existingMember.setMemberAddress(member.getMemberAddress());
			existingMember.setMemberEmail(member.getMemberEmail());
			return memberRepository.save(existingMember);
		}
		return null;
	}
//
//	public Member saveMember(Member member) {
//		// 執行資料庫儲存操作
//		return memberRepository.save(member);
//	}
//
//	public Member getMemberById(int memberId) {
//		// 根據會員ID從資料庫中獲取會員
//		return (Member) memberRepository.findByMemberId(memberId);
//	}

}
