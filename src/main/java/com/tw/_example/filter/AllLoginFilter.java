package com.tw._example.filter;

import java.io.IOException;
import java.util.HashSet;

import com.tw.employee.model.Employee;
import com.tw.member.model.Member;
import com.tw.vendor.model.Vendor;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// XXX 要用的時候請打開
// @WebFilter("*.html")
public class AllLoginFilter implements Filter {

	/**
	 * CookieFilter.java是使用cookie版本的
	 *
	 * 用session存物件比較簡單寫 但是只要瀏覽器一關掉就要重登
	 * 用cookie版本 好處是可以中途關掉瀏覽器 只要cookie沒有過期都會有效
	 */
	/**
	 * 這是大概的說明:
	 *
	 * 會員登入 if (uri.contains("/front-end/") && PROTECT.contains(htmlName))
	 *
	 * controller登入後 將Member物件 用"member-login" 塞到session的attribute裡
	 * EX:
	 * req.getSession().setAttribute("member-login", member);
	 *
	 * 這樣在這邊getAttribute("member-login") 如果有獲得會員物件 就表示已登入
	 *
	 *
	 * -----------------------------------------------------------------------------
	 *
	 * 員工登入 if (uri.contains("/back-end/") && !htmlName.equals("login.html"))
	 *
	 * controller登入後 將Employee物件 用"employee-login" 塞到session的attribute裡
	 * EX:
	 * req.getSession().setAttribute("employee-login", employee);
	 *
	 * 這樣在這邊getAttribute("employee-login") 如果有獲得員工物件 就表示已登入
	 *
	 * -----------------------------------------------------------------------------
	 *
	 * 廠商登入 if (uri.contains("/vendor-end/") && !htmlName.equals("login.html"))
	 *
	 * controller登入後 將Vendor物件 用"vendor-login" 塞到session的attribute裡
	 * EX:
	 * req.getSession().setAttribute("vendor-login", vendor);
	 *
	 * 這樣在這邊getAttribute("vendor-login") 如果有獲得廠商物件 就表示已登入
	 */
	private static HashSet<String> PROTECT = new HashSet<>();

	// 前台需要保護的網頁 (後台/廠商則是都要保護)
	static {
		PROTECT.add("ai_favorite.html");
		PROTECT.add("article_myfavorite.html");
		PROTECT.add("article_mylist.html");

		PROTECT.add("login.html");
		PROTECT.add("question_form.html");
		PROTECT.add("read_question_list.html");
		PROTECT.add("group_order.html");
		PROTECT.add("shopping_car.html");
		PROTECT.add("tickets_order.html");
	}

	@Override
	public void doFilter(final ServletRequest request,//
			final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

		// 轉成HttpServletRequest
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		final String uri = req.getRequestURI();
		final String htmlName = uri.substring(uri.lastIndexOf("/") + 1);

		// 會員登入
		if (uri.contains("/front-end/") && PROTECT.contains(htmlName)) {
			final Member member = getClassFromSession(req, "member-login", Member.class);

			// 沒有的話請重新登入
			if (member == null) {
				res.sendRedirect("/front-end/index.html?1");
				return;
			}

			// 員工登入
		} else if (uri.contains("/back-end/") && !htmlName.equals("login.html")) {
			final Employee employee = getClassFromSession(req, "employee-login", Employee.class);

			// 沒有的話請重新登入
			if (employee == null) {
				res.sendRedirect("/back-end/login.html");
				return;
			}
			// 這邊還要判斷權限可用的頁面

			// 廠商登入
		} else if (uri.contains("/vendor-end/") && !htmlName.equals("login.html")) {
			final Vendor vendor = getClassFromSession(req, "vendor-login", Vendor.class);

			// 沒有的話請重新登入
			if (vendor == null) {
				res.sendRedirect("/vendor-end/login.html");
				return;
			}
		}

		// 下面一位
		chain.doFilter(request, response);
	}

	/**
	 * 用指定的key將value從session的Attribute取出
	 * (對不起這段不知道怎麼用全中文解釋..)
	 *
	 * 再將value轉成指定的類別
	 */
	private <T> T getClassFromSession(final HttpServletRequest req, final String attrKey, final Class<T> clazz) {
		final Object obj = req.getSession().getAttribute(attrKey);
		if (obj != null && clazz.isInstance(obj)) {
			return clazz.cast(obj);
		}
		return null;
	}
}
