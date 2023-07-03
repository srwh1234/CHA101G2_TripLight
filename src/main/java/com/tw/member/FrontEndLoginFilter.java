package com.tw.member;

import java.io.IOException;
import java.util.HashSet;

import com.tw.member.model.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("*.html")
public class FrontEndLoginFilter implements Filter {
	 
	private static HashSet<String> PROTECT = new HashSet<>();
	static {
		PROTECT.add("ai_favorite.html");
		PROTECT.add("article_myfavorite.html");
		PROTECT.add("article_mylist.html");
		PROTECT.add("login.html");
		PROTECT.add("question_form.html");
		PROTECT.add("read_question_list.html");
		PROTECT.add("group_order.html");
		PROTECT.add("tickets_order.html");
		PROTECT.add("my_favorite.html");
		PROTECT.add("membership_rating.html");
		PROTECT.add("article_create.html");
	}

	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException  {
		final HttpServletRequest req = (HttpServletRequest)request;
		final HttpServletResponse res = (HttpServletResponse) response;
		
		final String uri = req.getRequestURI();
		final String htmlName = uri.substring(uri.lastIndexOf("/") + 1);
		
		if(uri.contains("/front-end") && PROTECT.contains(htmlName)) {
			final Member member = getClassFromSession(req, "member", Member.class);
			//System.out.println(member);
			
			if(member == null) {
				res.sendRedirect("/front-end/index.html?1");
				return;
			}
		}
		
		chain.doFilter(request, response);
		
	}

	
	private <T> T getClassFromSession(final HttpServletRequest req, final String attrKey, final Class<T> clazz) {
		final Object obj = req.getSession().getAttribute(attrKey);
		if(obj != null && clazz.isInstance(obj)) {
			return clazz.cast(obj);
		}
		return null;
	}

}
