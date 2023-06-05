package com.tw._example;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tw._example.hibernate.CouponDao;
import com.tw._example.jdbc.CouponDao2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/testServlet")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CouponDao couponDao;

	@Autowired
	private CouponDao2 couponDao2;

	@Override
	protected void doGet(final HttpServletRequest request,//
			final HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF8");

		response.getWriter().write("I am Servlet doGet\r\r");
		response.getWriter().write("Hibernate測試:\r");
		response.getWriter().write(couponDao.selectAll().toString() + "\r");

		response.getWriter().write("JDBC測試:\r");
		response.getWriter().write(couponDao2.selectAll().toString() + "\r");

	}

	@Override
	protected void doPost(final HttpServletRequest request, //
			final HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("I am Servlet doPost");
	}
}
