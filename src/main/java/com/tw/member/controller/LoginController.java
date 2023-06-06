package com.tw.member.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final String SESSION_ID_COOKIE_NAME = "memberId";

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        // 在這裡驗證用戶名和密碼
        // ...

        // 如果驗證成功，生成一個會話標識符並存入 cookie
        String sessionId = generateSessionId(); // 你需要實現這個方法
        Cookie cookie = new Cookie(SESSION_ID_COOKIE_NAME, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }




    private String generateSessionId() {
        // 在這裡實現生成會話標識符的邏輯
        // 例如，你可以生成一個隨機的 UUID，或者使用 JWT
        // 注意，你應該在伺服器端儲存這個會話標識符，並將其與用戶的帳號關聯起來
        // ...
        return "我是memberId";
    }
}

