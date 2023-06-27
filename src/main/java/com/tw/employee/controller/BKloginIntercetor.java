package com.tw.employee.controller;

import org.springframework.web.servlet.HandlerInterceptor;

import com.tw.employee.model.Employee;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BKloginIntercetor implements HandlerInterceptor {
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在進入控制器之前執行身份驗證邏輯
        // 如果未驗證，重定向到登入頁面
        if (!isUserLoggedIn(request)) {
            response.sendRedirect("http://localhost:8080/back-end/login.html");
            return false; // 
        }
        return true; // 
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        // 檢查用戶是否已登入
        return request.getSession().getAttribute("employee") != null;
    }
}
	    

    

