package com.tw.member.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.controller.LoginMemberController.MemberDetail;
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
	public MemberDetail getItem(final int id) {
		final Member member= memberRepository.findByMemberId(id);
		
		if (member== null) {
			return null;
		}
		final MemberDetail memberDetail= new MemberDetail(member);

		return memberDetail;
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
		//get old pwd	
//	    public boolean getPwd(int id, String password) {
//	        Member member =  memberRepository.findByMemberId(id);
//	        System.out.println("id=" + member.getMemberId());
//	        
//	        if ( password.equals(member.getMemberPassword())) {
//	        	System.out.println("get old pwd");
//	            return true; 
//	        }
//	        System.out.println("get none");
//	        return false; 
//	    }

	
	   public boolean validatePassword(int memberId, String password) {
	        Member member = memberRepository.findByMemberId(memberId);
	        String storedPassword = member.getMemberPassword();
	        System.out.println(password.trim().equals(storedPassword));
	        if (password.trim().equals(storedPassword)) {
	            System.out.println("get old pwd");
	            return true;
	        }
	        System.out.println("get none");
	        return false;
	    }

	
	
	    // change password
	    public void changePwd(int id, String password) {
	    	Member existingMember = memberRepository.findByMemberId(id);
	    	if(existingMember != null) {
	    		existingMember.setMemberPassword(password);
	    		memberRepository.save(existingMember);
	    		System.out.println("changed pwd successfully");
	    	}
	    }
}
