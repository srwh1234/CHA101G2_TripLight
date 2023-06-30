package com.tw.ticket.redis.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tw.ticket.Config;
import com.tw.ticket.MyUtils;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.redis.TicketImageRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 一種快取的概念
 * 是如果有啟動Redis伺服器，就讀取裡面的資料
 * 如果有資料就回傳，
 * 如果沒資料就讀取資料庫，並存在Redis內，下次要用就可以直接取用
 */
@Repository
public class TicketImageRedisImpl implements TicketImageRedis {

	private static String REDIS_KEY = "TicketImg:";

	@Autowired
	private Config config;

	@Autowired
	private TicketImageRepository repository;

	@Autowired
	private JedisPool jedisPool;

	// 指定編號的TicketImage
	@Override
	public TicketImage findById(final int id) {
		if (!config.isRedisServerStarted()) {
			return repository.findById(id).orElse(null);
		}
		final String key = REDIS_KEY + id;
		TicketImage result;

		try (Jedis jedis = jedisPool.getResource()) {
			final String jsonString = jedis.get(key);

			if (!MyUtils.isEmpty(jsonString)) {
				return MyUtils.fromJson(jsonString, TicketImage.class);
			}
			result = repository.findById(id).orElse(null);
			jedis.set(key, MyUtils.toJson(result));
		}
		return result;
	}

	// 新增全部
	@Override
	public List<TicketImage> saveAll(final List<TicketImage> ticketImages) {
		final List<TicketImage> result = repository.saveAll(ticketImages);

		if (config.isRedisServerStarted()) {
			try (Jedis jedis = jedisPool.getResource()) {
				for (final TicketImage ticketImage : result) {
					final String key = REDIS_KEY + ticketImage.getId();
					jedis.set(key, MyUtils.toJson(ticketImage));
				}
			}
		}
		return result;
	}

	// 新增
	@Override
	public TicketImage save(final TicketImage ticketImage) {
		final TicketImage result = repository.save(ticketImage);

		if (config.isRedisServerStarted()) {
			try (Jedis jedis = jedisPool.getResource()) {
				final String key = REDIS_KEY + result.getId();
				jedis.set(key, MyUtils.toJson(result));
			}
		}

		return result;
	}

	// 刪除
	@Override
	public void delete(final TicketImage ticketImage) {
		repository.delete(ticketImage);

		if (config.isRedisServerStarted()) {
			try (Jedis jedis = jedisPool.getResource()) {
				final String key = REDIS_KEY + ticketImage.getId();
				jedis.del(key);
			}
		}
	}
}
