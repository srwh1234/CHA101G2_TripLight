package com.tw.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

	// 單純不想在application.properties配置
	@Bean
	public JedisPool jedisPool() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();

		poolConfig.setJmxEnabled(false); // 停用 JMX
		final JedisPool jedisPool = new JedisPool(poolConfig);
		return jedisPool;
	}
}
