package com.tw.member.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	//根據用戶名稱email查詢數據
	public Member findByMemberEmail(String email);

	public Member findByMemberId(int memberId);

	public void save(String memberPic);	



 

}
