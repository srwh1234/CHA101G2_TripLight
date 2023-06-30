package com.tw.game.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GameWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    GameSessionCheckInterceptor gameSessionCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 註冊攔截器，並添加攔截路徑和排除路徑
        registry.addInterceptor(gameSessionCheckInterceptor)
                .addPathPatterns("/game-end/*.html") // 攔截所有game-end資料夾以 .html 結尾的路徑
                .excludePathPatterns("/**/login.html"); // 排除符合 **/index.html 的路徑
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加靜態資源處理器，指定靜態資源路徑
        registry.addResourceHandler("/static/**") // 使用 "/static/game/**" 來映射靜態資源的請求。
                // 將靜態資源的存放路徑設定為 classpath:/static/，表示靜態資源存放在類別路徑下的 static 目錄
                .addResourceLocations("classpath:/static/");
    }
}

