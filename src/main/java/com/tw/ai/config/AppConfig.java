package com.tw.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${API_KEY}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}

