//package com.tw._example.filter;
//
//import com.tw.member.model.Member;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//public class SessionCheckInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        Member user = (Member) (session != null ? session.getAttribute("user") : null);
//        if (user == null) {
//            response.sendRedirect("/front-end/index.html");
//            return false;
//        } else {
//            return true;
//        }
//    }
//}