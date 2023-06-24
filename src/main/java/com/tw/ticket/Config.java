package com.tw.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.maps.GeoApiContext;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class Config implements WebMvcConfigurer {

	@Getter
	@Value("${ecpay-return-url}")
	private String ecpayReturnUrl;

	@Getter
	@Value("${google-api-key}")
	private String googleApiKey;

	@Setter
	@Getter
	private boolean isRedisServerStarted;

	// Google Map Api
	@Bean
	public GeoApiContext geoApiContext() {
		return new GeoApiContext.Builder().apiKey(getGoogleApiKey()).build();
	}

	// 新增：告訴 Spring MVC 在處理非同步請求時使用你配置的 TaskExecutor，以便提供更好的性能和容錯能力。
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setTaskExecutor(threadPoolTaskExecutor());
	}


	// 單純不想在application.properties配置
	@Bean
	public JedisPool jedisPool() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();

		poolConfig.setJmxEnabled(false); // 停用 JMX
		final JedisPool jedisPool = new JedisPool(poolConfig);
		return jedisPool;
	}

	// 執行緒池 (搭配@Async)
	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("MyThreadPool-");
		executor.initialize();
		return executor;
	}
}
