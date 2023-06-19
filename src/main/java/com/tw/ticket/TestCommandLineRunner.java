package com.tw.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class TestCommandLineRunner implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(TestCommandLineRunner.class);

	@Autowired
	private Config config;

	@Autowired
	private JedisPool jedisPool;

	@Override
	public void run(final String... args) {

		// 判斷redis伺服器有無開啟
		try (Jedis jedis = jedisPool.getResource()) {
			config.setRedisServerStarted(true);
		} catch (final Exception e) {
			config.setRedisServerStarted(false);
		} finally {
			log.info("Redis Server Started:" + config.isRedisServerStarted());
		}
	}
}
