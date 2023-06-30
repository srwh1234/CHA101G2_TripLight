package com.tw.ticket.redis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tw.ticket.Config;
import com.tw.ticket.controller.CartController.AddReqDto;
import com.tw.ticket.controller.CartController.CartDto;
import com.tw.ticket.controller.CartController.DescTicketDto;
import com.tw.ticket.controller.CartController.QuantityReqDto;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketCart;
import com.tw.ticket.model.dao.TicketCartRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.redis.TicketCartsRedis;
import com.tw.ticket.service.CartService;
import com.tw.ticket.service.PromotionService;
import com.tw.ticket.service.impl.ImageServiceImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
public class TicketCartsRedisImpl implements TicketCartsRedis {

	@Autowired
	private Config config;

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private TicketCartRepository ticketCartRepository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private ImageServiceImpl imageService;

	@Autowired
	private PromotionService promotionService;
	private static String REDIS_KEY = "TicketCart:";

	/**
	 * 票券購物車清單 (Redis)
	 *
	 * @param sessionId
	 * @return
	 */
	@Override
	public List<CartDto> getItems(final String sessionId) {
		final List<CartDto> result = new ArrayList<>();
		if (config.isRedisServerStarted()) {
			final String key = REDIS_KEY + sessionId;
			try (Jedis jedis = jedisPool.getResource()) {
				final Map<String, String> cartItems = jedis.hgetAll(key);

				for (final Map.Entry<String, String> entry : cartItems.entrySet()) {
					final int ticketId = Integer.parseInt(entry.getKey());
					int quantity = Integer.parseInt(entry.getValue());
					final Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

					if (ticket == null) {
						continue;
					}
					final DescTicketDto descDto = new DescTicketDto(ticket);
					// 可用數量
					final int available = snRepository.countUsableSn(ticketId);
					descDto.setAvailable(available);

					// 促銷
					descDto.setPromotion(promotionService.getItem(ticketId));

					// 圖片
					descDto.setImage(imageService.findImgUrl(ticket.getTicketId()));

					if (quantity > available) {
						quantity = available;
					}

					final CartDto cartDto = new CartDto();
					cartDto.setQuantity(quantity);
					cartDto.setTicket(descDto);

					result.add(cartDto);
				}
			}
		}
		return result;
	}

	/**
	 * 放入購物車 (Redis)
	 *
	 * @param reqDto
	 * @return
	 */
	@Override
	public int addItem(final AddReqDto reqDto) {
		if (!config.isRedisServerStarted()) {
			return CartService.ADD_CART_LOGIN;
		}
		try (Jedis jedis = jedisPool.getResource()) {
			// Hash
			final String key = REDIS_KEY + reqDto.getSessionId();
			jedis.hincrBy(key, String.valueOf(reqDto.getTicketId()), reqDto.getQuantity());
			// 存活1周
			jedis.expire(key, 7 * 86400L);
		}
		return CartService.ADD_CART_OK;
	}

	/**
	 * 變更數量 (Redis)
	 *
	 * @param reqDto
	 * @return
	 */
	@Override
	public boolean updateItem(final QuantityReqDto reqDto) {
		if (!config.isRedisServerStarted()) {
			return false;
		}
		try (Jedis jedis = jedisPool.getResource()) {
			// Hash
			final String key = REDIS_KEY + reqDto.getSessionId();
			jedis.hincrBy(key, String.valueOf(reqDto.getTicketId()), reqDto.getModify());
			// 存活1周
			jedis.expire(key, 7 * 86400L);
		}
		return true;
	}

	/**
	 * 移除購物車品項 (Redis)
	 *
	 * @param sessionId
	 * @param ticketId
	 */
	@Override
	public void removeItem(final String sessionId, final int ticketId) {
		if (config.isRedisServerStarted()) {
			try (Jedis jedis = jedisPool.getResource()) {
				// Hash
				final String key = REDIS_KEY + sessionId;
				jedis.hdel(key, String.valueOf(ticketId));
			}
		}
	}

	/**
	 * 將Redis資料存到資料庫
	 *
	 * @param memberId
	 * @param sessionId
	 */
	@Override
	public void convertItems(final int memberId, final String sessionId) {
		if (config.isRedisServerStarted()) {
			final String key = REDIS_KEY + sessionId;
			try (Jedis jedis = jedisPool.getResource()) {
				final Map<String, String> cartItems = jedis.hgetAll(key);
				for (final Map.Entry<String, String> entry : cartItems.entrySet()) {
					final int ticketId = Integer.parseInt(entry.getKey());
					final int quantity = Integer.parseInt(entry.getValue());

					TicketCart cart = ticketCartRepository.findByKeyMemberIdAndKeyTicketId(memberId, ticketId);

					if (cart != null) {
						cart.addQuantity(quantity);
					} else {
						cart = new TicketCart(memberId, ticketId, quantity);
					}
					ticketCartRepository.save(cart);
				}
				jedis.del(key);
			}
		}
	}

}
