//package com.tw._example.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.stereotype.Component;
//
//
//@Component
//
//@WebFilter( {"/*"} )
//public class LoginFilter implements Filter {
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
//			throws IOException, ServletException {
//		//System.out.println("START doFilter2");
//		filterchain.doFilter(request, response);
//		//System.out.println("END   doFilter2");
//	}
//	
////	@Override
////	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
////			throws IOException, ServletException {
////
////		HttpServletRequest req = (HttpServletRequest) request;
////		HttpServletResponse res = (HttpServletResponse) response;
////		// 取得session
////		HttpSession session = req.getSession();
////		//放行...文件
////		String uri = new String(req.getRequestURI());  
////		//System.out.println("filter exist");
//////		if(uri.contains("/css") || uri.contains("/images") || uri.contains("/js")) {
//////			chain.doFilter(request, response);
//////			return;
//////		}
////
////		
////		// 從session判斷會員是否登入過
//////		if (account == null) {
//////			res.sendRedirect(req.getContextPath() + "/front-end/index.html");
//////		} else {
//////			chain.doFilter(request, response);
//////		}
////		Object account = session.getAttribute("member");
////		if(account != null ) {
////			//System.out.println("good job");
////			if(uri.contains("login") || uri.contains("favorite") || uri.contains("co")) {
////				chain.doFilter(request, response);
////			}
////		}else {
////			//System.out.println("nono");
////			res.sendRedirect(req.getContextPath() + "/front-end/index.html");
////		}
////	}
//	
//	@Override
//	public void destroy() {
//	
//	}
//
//}
