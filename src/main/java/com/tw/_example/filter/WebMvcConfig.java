//package com.tw._example.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    SessionCheckInterceptor sessionCheckInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(sessionCheckInterceptor)
//                .addPathPatterns("/**")  // 指定需要攔截的路徑
//                .excludePathPatterns("/login","/front-end/index.html", "/front-end/css/**", "/front-end/js/**","/front-end/images/**","/front-end/slick/**","/front-end/webfonts/**");  // 指定不需要攔截的路徑
//    }
//}
//
