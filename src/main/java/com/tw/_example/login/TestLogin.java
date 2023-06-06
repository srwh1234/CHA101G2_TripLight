package com.tw._example.login;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RestController
public class TestLogin {

	public static final String SESSION_ID_COOKIE_NAME = "memberId";

	/*
	 * 測試相關的html
	 * http://localhost:8080/front-end/_test_login.html
	 *
	 * CookieFilter.java
	 * TestLogin.java
	 * */
	@PostMapping("/testLogin")
	public ResponseEntity<Object> testLogin(            //
			@RequestBody final Map<String, String> map,    // 用一個Map裝客戶端的請求資料
			final HttpServletRequest request,            //
			final HttpServletResponse response) {        //

		// 取值(json)
		final String account = map.get("account");
		final String password = map.get("password");

		// 如果驗證成功，生成一個會話標識符並存入 cookie
		if (account.equals("1111") && password.equals("1111")) {

			// 取得驗證成功當下的session id
			final String sessionId = request.getSession().getId();

			// 存在一個共用的map裡 這邊用的是ServletContext
			request.getServletContext().setAttribute(sessionId, "這裡可以放隨便一個物件");

			// 寫cookie 並回應給客戶端
			final Cookie cookie = new Cookie(SESSION_ID_COOKIE_NAME, sessionId);
			cookie.setMaxAge(3600);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);

			return ResponseEntity.ok().build();
		}

		// 驗證不成功
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}