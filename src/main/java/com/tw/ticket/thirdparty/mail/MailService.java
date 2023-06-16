package com.tw.ticket.thirdparty.mail;

public interface MailService {

	// 發送mail給指定對象
	public void sendEmail(String to, String subject, String text);
}
