package com.tw.ticket;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

// 請求的文字編碼
// 加了求心安
//@WebFilter("/*")
public class CharEncodingFilter implements Filter {
	@Override
	public void doFilter(final ServletRequest request,//
			final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

		request.setCharacterEncoding("UTF8");

		// 下面一位
		chain.doFilter(request, response);
	}
}
