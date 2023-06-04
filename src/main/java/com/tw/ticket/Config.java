package com.tw.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class Config {

	@Getter
	@Value("${ecpay-return-url}")
	private String ecpayReturnUrl;

}
