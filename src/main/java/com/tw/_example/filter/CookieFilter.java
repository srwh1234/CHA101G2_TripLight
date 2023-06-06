package com.tw._example.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("*.html")
public class CookieFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request,//
			final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

		// 轉成HttpServletRequest才有cookies
		final HttpServletRequest httpRequest = (HttpServletRequest) request;

		final Cookie[] cookies = httpRequest.getCookies();

		// 這邊可以判斷cookie夾帶的資訊
		System.out.println("httpRequest=" + httpRequest.getRequestURL());

		// 下面一位
		chain.doFilter(request, response);
	}

}
