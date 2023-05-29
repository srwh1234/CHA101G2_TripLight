package com.tw.ticket.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.tw.ticket.dao.TicketImageRepository;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private TicketImageRepository repository;

	@Override
	public byte[] findImg(final long id) {
		final Optional<TicketImage> optional = repository.findById(id);

		// 沒有指定編號的圖片
		if (optional.isEmpty()) {
			final ClassPathResource resource = new ClassPathResource("images/bb.gif");

			byte[] bytes = null;
			try (final InputStream is = resource.getInputStream();) {
				bytes = new byte[is.available()];
				is.read(bytes);

			} catch (final IOException e) {
				e.printStackTrace();
			}
			return bytes;
		}
		final TicketImage img = repository.findById(id).get();

		return img.getImage();

	}

}