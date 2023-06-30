package com.tw.vendor.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class VendorWebMvcConfigurer implements WebMvcConfigurer {
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new BKVendorLoginIntercetor()) //註冊要使用的類別
		.addPathPatterns("/vendor-end/*.html") // 指定攔截的路徑只攔截到vendor-end的html
        .excludePathPatterns("/vendor-end/login.html"); // 排除登入頁面的路徑
	
	}

}
