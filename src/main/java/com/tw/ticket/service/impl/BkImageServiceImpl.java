package com.tw.ticket.service.impl;

import static com.tw.ticket.service.ImageService.IMG_URL;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.redis.TicketImageRedis;
import com.tw.ticket.service.BkImageService;

@Service
public class BkImageServiceImpl implements BkImageService {
	private static final Logger log = LoggerFactory.getLogger(BkImageServiceImpl.class);

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketImageRedis ticketImgRedis;

	/**
	 * 新增多張圖片
	 *
	 * @param ticketId
	 * @param files
	 */
	@Override
	public void addImages(final int ticketId, final MultipartFile[] files) {
		final List<TicketImage> ticketImages = new ArrayList<>();
		for (final MultipartFile file : files) {
			try {
				final byte[] array = file.getBytes();

				final TicketImage image = new TicketImage();
				image.setTicketId(ticketId);
				image.setImage(array);
				image.setUploadTime(Timestamp.from(Instant.now()));
				ticketImages.add(image);
			} catch (final IOException e) {
				log.error(e.getLocalizedMessage(), e);
				continue;
			}
		}
		ticketImgRedis.saveAll(ticketImages);
	}

	/**
	 * 新增圖片
	 *
	 * @param ticketId
	 * @param file
	 * @return
	 */
	@Override
	public String addImage(final int ticketId, final MultipartFile file) {
		String result = null;

		final Ticket ticket = repository.findById(ticketId).orElse(null);

		if (ticket == null) {
			return result;
		}

		try {
			final byte[] array = file.getBytes();

			final TicketImage image = new TicketImage();
			image.setTicketId(ticketId);
			image.setImage(array);
			image.setUploadTime(Timestamp.from(Instant.now()));

			ticketImgRedis.save(image);

			result = IMG_URL + image.getId();
		} catch (final IOException e) {
			log.error(e.getLocalizedMessage(), e);
		}

		return result;
	}

	/**
	 * 移除圖片
	 * 
	 * @param imageId
	 * @return
	 */
	@Override
	public boolean removeImage(final int imageId) {
		final TicketImage image = ticketImgRedis.findById(imageId);

		if (image == null) {
			return false;
		}
		ticketImgRedis.delete(image);
		return true;
	}

}
