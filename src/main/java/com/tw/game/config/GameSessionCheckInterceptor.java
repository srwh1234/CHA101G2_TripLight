package com.tw.game.config;


import com.tw.game.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GameSessionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 取得當前請求的 HttpSession 物件
        HttpSession session = request.getSession();
        // 從會話中取得名為 "member" 的屬性，並將其轉換為 Member 物件
        User user = (User) (session != null ? session.getAttribute("user") : null);
        if (user == null) {
            // 若使用者未登入，則將請求重新導向至登入頁面
            response.sendRedirect("/game-end/login.html");
            return false;
        } else {
            // 若使用者已登入，則允許繼續處理該請求
            return true;
        }
    }
}

