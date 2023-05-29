package com.tw.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
		final TicketImage img = repository.findById(id).get();

		if (img == null) {
			return null;
		}
		return img.getImage();

	}

}
