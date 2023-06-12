package com.tw.ticket.service.impl;

import static com.tw.ticket.controller.ImageController.IMG_URL;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.service.BkImageService;

@Service
public class BkImageServiceImpl implements BkImageService {
	private static final Logger log = LoggerFactory.getLogger(BkImageServiceImpl.class);

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketImageRepository ticketImageRepository;

	// 後台新增圖片集合
	@Override
	public void addImages(final int ticketId, final MultipartFile[] files) {
		final List<TicketImage> ticketImages = new ArrayList<>();
		for (final MultipartFile file : files) {
			try {
				final byte[] array = file.getBytes();

				final TicketImage image = new TicketImage();
				image.setTicketId(ticketId);
				image.setImage(array);
				image.setUploadTime(new Timestamp(System.currentTimeMillis()));
				ticketImages.add(image);
			} catch (final IOException e) {
				log.error(e.getLocalizedMessage(), e);
				continue;
			}
		}
		ticketImageRepository.saveAll(ticketImages);
	}

	// 後台票券新增圖片
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
			image.setUploadTime(new Timestamp(System.currentTimeMillis()));

			ticketImageRepository.save(image);

			result = IMG_URL + image.getId();
		} catch (final IOException e) {
			log.error(e.getLocalizedMessage(), e);
		}

		return result;
	}

	// 後台票券移除圖片
	@Override
	public boolean removeImage(final int employeeId, final int imageId) {
		final TicketImage image = ticketImageRepository.findById(imageId).orElse(null);

		if (image == null) {
			return false;
		}
		ticketImageRepository.delete(image);
		return true;
	}

}
