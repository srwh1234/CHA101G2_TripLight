package com.tw.employee.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

	  @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new BKloginIntercetor())
	        		.addPathPatterns("/**/*.html") // 指定攔截的路徑只攔截html
	                .excludePathPatterns("/back-end/login.html"); // 排除登入頁面的路徑
	    }
}
