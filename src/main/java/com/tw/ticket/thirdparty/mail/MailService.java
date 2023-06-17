package com.tw.ticket.thirdparty.mail;

public interface MailService {

	// 發送mail給指定對象(純文字)
	public void sendEmail(String to, String subject, String text);

	// 發送mail給指定對象(html)
	public void sendHtmlEmail(String to, String subject, String html);
}
