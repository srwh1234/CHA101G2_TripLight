package com.tw.ticket;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.tw.employee.model.Employee;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 要有登入員工才能進行操作
 *
 * OncePerRequestFilter: 同個request 確保自身只過濾一次
 */
@WebFilter("/bk/*")
public class BkRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res, //
			final FilterChain filterChain) throws ServletException, IOException {

		final Employee employee = (Employee) req.getSession().getAttribute("employee");

		// 沒登入 送他一個401
		if (employee == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		filterChain.doFilter(req, res);
	}

}
