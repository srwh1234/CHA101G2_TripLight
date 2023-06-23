package com.tw.member.controller;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.member.service.LoginService;
import com.tw.member.service.LoginService.MailService;

@RestController
public class ForgetPwdController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private final MailService mailService;
	@Autowired
	private MemberRepository memberRepository;

    public ForgetPwdController(MailService mailService) {
    	this.mailService = mailService	;	
	}
    //忘記密碼從email送新密碼
    @PostMapping("/sendMail")
	public void someMethod(@RequestParam String forgetPwdEmail) {
    	String randomPassword = generateRandomPassword();
    	System.out.println(randomPassword);
		mailService.sendMail(forgetPwdEmail, "TripLight會員新密碼", "這是您的新密碼"+randomPassword);
		//存新密碼到資料庫
		Member member = memberRepository.findByMemberEmail(forgetPwdEmail);
		if(member != null) {
			member.setMemberPassword(randomPassword);
			memberRepository.save(member);
		}
	}
	//隨機產生密碼
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

}
