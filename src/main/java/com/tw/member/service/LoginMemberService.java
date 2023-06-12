package com.tw.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.controller.LoginMemberController.MemberDetail;
import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.TicketDetailController.DetailResponse;
import com.tw.ticket.model.Ticket;

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
	public MemberDetail getItem(final int id) {
		final Member member= memberRepository.findByMemberId(id);

		if (member== null) {
			return null;
		}
		final MemberDetail memberDetail= new MemberDetail(member);

		return memberDetail;
	}
	// 查詢指定ID
//	public Member getMemberById(int id, Member member) {
//		Member existingMember = memberRepository.findByMemberId(id);
//		existingMember.setMemberNameLast(member.getMemberNameLast());
//		existingMember.setMemberNameFirst(member.getMemberNameFirst());
//		existingMember.setMemberIdCard(member.getMemberIdCard());
//		existingMember.setMemberBirth(member.getMemberBirth());
//		existingMember.setMemberPhone(member.getMemberPhone());
//		existingMember.setMemberGender(member.getMemberGender());
//		existingMember.setMemberCity(member.getMemberCity());
//		existingMember.setMemberDist(member.getMemberDist());
//		existingMember.setMemberAddress(member.getMemberAddress());
//		existingMember.setMemberEmail(member.getMemberEmail());
//		return existingMember;
//	}
	// 新增
//	public Member createMember(Member member) {
//		return memberRepository.save(member);
//	}

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
