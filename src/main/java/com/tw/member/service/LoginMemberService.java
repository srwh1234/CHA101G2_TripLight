package com.tw.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

@Service
public class LoginMemberService {
	@Autowired
	private MemberRepository memberRepository;

//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	// 查詢全部
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

		// 查詢指定ID
    public Member getMemberById(int id) {
        return memberRepository.findById(id).orElse(null);
    }

	// 新增
	public Member createMember(Member member) {
		return memberRepository.save(member);
	}

	// update

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

}
