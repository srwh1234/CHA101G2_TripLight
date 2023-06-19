package com.tw.ticket.model.redis.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tw.ticket.Config;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.model.redis.TicketImageRedis;

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
	private RedisTemplate<String, Object> redisTemplate;

	// 指定編號的TicketImage
	@Override
	public TicketImage findById(final int id) {
		if (!config.isRedisServerStarted()) {
			return repository.findById(id).orElse(null);
		}
		final String key = REDIS_KEY + id;

		final Object object = redisTemplate.opsForValue().get(key);

		if (object != null) {
			return (TicketImage) object;
		}
		final TicketImage result = repository.findById(id).orElse(null);

		if (result != null) {
			redisTemplate.opsForValue().set(key, result);
		}
		return result;
	}

	// 新增全部
	@Override
	public List<TicketImage> saveAll(final List<TicketImage> ticketImages) {
		final List<TicketImage> result = repository.saveAll(ticketImages);
		if (config.isRedisServerStarted()) {
			for (final TicketImage ticketImage : result) {
				final String key = REDIS_KEY + ticketImage.getId();
				redisTemplate.opsForValue().set(key, ticketImage);
			}
		}
		return result;
	}

	// 新增
	@Override
	public TicketImage save(final TicketImage ticketImage) {
		final TicketImage result = repository.save(ticketImage);
		if (config.isRedisServerStarted()) {
			final String key = REDIS_KEY + result.getId();
			redisTemplate.opsForValue().set(key, result);
		}
		return result;
	}

	// 刪除
	@Override
	public void delete(final TicketImage ticketImage) {
		repository.delete(ticketImage);
		if (config.isRedisServerStarted()) {
			final String key = REDIS_KEY + ticketImage.getId();
			redisTemplate.delete(key);
		}
	}
}
