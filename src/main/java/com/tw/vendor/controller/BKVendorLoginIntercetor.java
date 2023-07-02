package com.tw.vendor.controller;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BKVendorLoginIntercetor implements HandlerInterceptor {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		// 在進入控制器之前執行身份驗證邏輯
		// 如果未驗證，重定向到登入頁面
		if (!isUserLoggedIn(request)) {
			response.sendRedirect("/vendor-end/login.html");
			return false; //
		}
		return true; //
	}

	private boolean isUserLoggedIn(final HttpServletRequest request) {
		// 檢查用戶是否已登入
		return request.getSession().getAttribute("vendor") != null;
	}

}
