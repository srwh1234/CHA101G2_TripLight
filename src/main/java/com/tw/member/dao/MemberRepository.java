package com.tw.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	//
}
