package com.tw.member.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

@Service
public class LoginService {
	@Autowired
	private MemberRepository memberRepository;

	  //註冊=============================================================
	public boolean register(String email, String password, String account) {
        Member existingMember = memberRepository.findByMemberEmail(email);
        if (existingMember != null) {
            return false; // 用戶名已存在
        }
        Member member = new Member();
        member.setMemberEmail(email);
        member.setMemberPassword(password);
        member.setMemberAccount(account);
        member.setMemberJoinTime(new Date());// 新增註冊時間
        member.setMemberStatus(1);
        member.setMemberGrade(0);
        memberRepository.save(member);        
        return true; // 註冊成功
    }
	  //登入=============================================================
	  public Integer login(String email, String password) {
	        Member member = memberRepository.findByMemberEmail(email);
	        if (member != null && password.equals(member.getMemberPassword())) {
	        	//是否停權
	            if (member.getMemberStatus() == 2) {
	                return -1; 
	            }
	            return member.getMemberId(); 
	        }
	        return 0;
	    }
	  public Member getMember(String email) {
		  Member member = memberRepository.findByMemberEmail(email);
		  return member;
	  }
	  public String getName(int id) {
		  Member member = memberRepository.findByMemberId(id);
		  String name = member.getMemberNameFirst();
		  return name;
	  }
	  //連信箱密碼驗證=============================================================
	  @Service
	  public class MailService {
		    
		    private final JavaMailSender mailSender;

		    @Autowired
		    public MailService(JavaMailSender mailSender) {
		        this.mailSender = mailSender;
		    }

		    public void sendMail(String to, String subject, String messageText) {
		        SimpleMailMessage message = new SimpleMailMessage();
		        message.setTo(to);
		        message.setSubject(subject);
		        message.setText(messageText);
		        mailSender.send(message);
		        System.out.println("郵件傳送成功!");
		    }
		}


}
