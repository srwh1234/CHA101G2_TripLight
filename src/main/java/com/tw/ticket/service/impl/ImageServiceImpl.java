package com.tw.ticket.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.model.redis.TicketImageRedis;
import com.tw.ticket.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

	@Autowired
	private TicketImageRepository repository;

	@Autowired
	private TicketImageRedis ticketImgRedis;

	/**
	 * 找出指定圖片 沒有則回傳預設圖
	 *
	 * @param id 圖票編號
	 * @return
	 */
	@Override
	public byte[] findImg(final int id) {
		final TicketImage image = ticketImgRedis.findById(id);

		// 沒有指定編號的圖片
		if (image == null) {
			final ClassPathResource resource = new ClassPathResource("images/aa.gif");

			byte[] bytes = null;
			try (final InputStream is = resource.getInputStream();) {
				bytes = new byte[is.available()];
				is.read(bytes);

			} catch (final IOException e) {
				log.error(e.getLocalizedMessage(), e);
			}
			return bytes;
		}
		return image.getImage();
	}

	/**
	 * 找出指定票券的所有圖片URL
	 *
	 * @param ticketId 票券編號
	 * @return
	 */
	@Override
	public List<String> findImgUrls(final int ticketId) {
		final ArrayList<String> result = new ArrayList<>();
		final List<Integer> arrays = repository.findIdsByTicketId(ticketId);
		if (arrays.isEmpty()) {
			result.add(IMG_URL + 0);
			return result;
		}
		for (final int id : arrays) {
			result.add(IMG_URL + id);
		}
		return result;
	}

	/**
	 * 找出指定票券的圖片URL
	 *
	 * @param ticketId 票券編號
	 * @return
	 */
	@Override
	public String findImgUrl(final int ticketId) {
		final List<Integer> arrays = repository.findIdsByTicketId(ticketId);

		if (arrays.isEmpty()) {
			return IMG_URL + 0;
		}
		return IMG_URL + arrays.get(0);
	}

}
