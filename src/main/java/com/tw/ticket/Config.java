package com.tw.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.maps.GeoApiContext;

import lombok.Getter;
import lombok.Setter;

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

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		return new LettuceConnectionFactory(config);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		// 序列化器 先不要
		// template.setKeySerializer(new StringRedisSerializer());
		// template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// template.setHashKeySerializer(new StringRedisSerializer());
		// template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		// template.afterPropertiesSet();
		return template;
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
