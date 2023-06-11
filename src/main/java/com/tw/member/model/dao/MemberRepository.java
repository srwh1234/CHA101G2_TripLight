package com.tw.member.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findMemberByMemberEmail(String email);
	
	public Member findByMemberId(int memberId);

}
