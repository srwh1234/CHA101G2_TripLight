package com.tw._example.filter;

import java.io.IOException;

import com.tw._example.login.TestLogin;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("*.html")
public class CookieFilter implements Filter {

	/*
	 * 測試相關的html
	 * http://localhost:8080/front-end/_test_login.html
	 *
	 * CookieFilter.java
	 * TestLogin.java
	 * */

	@Override
	public void doFilter(final ServletRequest request,//
			final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

		// 轉成HttpServletRequest才有cookies
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		final String uri = req.getRequestURI();
		final String htmlName = uri.substring(uri.lastIndexOf("/") + 1);

		// 這邊..
		// 正確的做法應該是@WebFilter 或其他設定方式 填入要檢驗的位置
		if (htmlName.equals("shopping_car.html") || htmlName.equals("tickets_order.html")) {

			// 這邊可以判斷cookie夾帶的資訊
			final Cookie[] cookies = req.getCookies();

			String validCode = null;
			if (cookies != null) {
				for (final Cookie cookie : cookies) {

					// 我們定義的key
					if (cookie.getName().equals(TestLogin.SESSION_ID_COOKIE_NAME)) {
						validCode = cookie.getValue();

						// 找看看有沒有 [驗證成功當下的session id]
						final Object ret = request.getServletContext().getAttribute(validCode);

						// 沒有的話請重新登入
						if (ret == null) {
							res.sendRedirect("/front-end/_test_login.html");
							return;
						}
					}
				}
			}
			// 沒有的話請重新登入
			if (validCode == null) {
				res.sendRedirect("/front-end/_test_login.html");
				return;
			}

		}

		// 下面一位
		chain.doFilter(request, response);
	}

}
// =======
// package com.tw._example.filter;
//
// import java.io.IOException;
//
// import com.tw.member.model.Member;
// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.annotation.WebFilter;
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;
//
// public class CookieFilter implements Filter {
//
//
//// public void doFilter(final ServletRequest request,//
//// final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
////
//// // 轉成HttpServletRequest才有cookies
//// final HttpServletRequest httpRequest = (HttpServletRequest) request;
////
//// final Cookie[] cookies = httpRequest.getCookies();
////
//// // 這邊可以判斷cookie夾帶的資訊
//// System.out.println("httpRequest=" + httpRequest.getRequestURL());
////
//// // 下面一位
//// chain.doFilter(request, response);
//// }
// @Override
// public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
// throws IOException, ServletException {
// HttpServletRequest request = (HttpServletRequest) req;
// HttpServletResponse response = (HttpServletResponse) res;
// HttpSession session = request.getSession(false);
// Member user = (Member) session.getAttribute("user");
// if (user == null) {
// response.sendRedirect(request.getContextPath() + "/front-end/index.html");
// } else {
// chain.doFilter(req, res);
// }
// }
//
// }
