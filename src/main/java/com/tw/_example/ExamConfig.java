package com.tw._example;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExamConfig {
	@Bean
	public ServletRegistrationBean<TestJsp> testJspRegistrationBean() {
		final ServletRegistrationBean<TestJsp> registrationBean //
				= new ServletRegistrationBean<>(new TestJsp(), "/testJsp");
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}
}
