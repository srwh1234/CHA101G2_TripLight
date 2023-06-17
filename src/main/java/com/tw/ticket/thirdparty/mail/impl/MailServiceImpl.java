package com.tw.ticket.thirdparty.mail.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tw.ticket.thirdparty.mail.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailSender;

	// 發送mail給指定對象
	@Override
	public void sendEmail(final String to, final String subject, final String text) {
		final SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}

	// 發送mail給指定對象(html)
	@Override
	public void sendHtmlEmail(final String to, final String subject, final String html) {
		try {
			final MimeMessage message = javaMailSender.createMimeMessage();
			final MimeMessageHelper messageHelper = new MimeMessageHelper(message);

			messageHelper.setTo(to);

			messageHelper.setSubject(subject);
			messageHelper.setText(html, true);
			javaMailSender.send(message);
		} catch (final MessagingException e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}
}
