package com.tw._example;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestJsp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(final HttpServletRequest request,//
			final HttpServletResponse response) throws ServletException, IOException {

		final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/TestJsp.jsp");
		dispatcher.forward(request, response);
	}
}
