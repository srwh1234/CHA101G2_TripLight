package com.tw.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.maps.GeoApiContext;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class Config {

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
