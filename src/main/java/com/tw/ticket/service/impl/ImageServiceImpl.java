package com.tw.ticket.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	// XXX http://localhost:8080 VsCode測試才要加
	// 獲得圖片的URL
	public static String IMG_URL = "http://localhost:8080/img/";

	@Autowired
	private TicketImageRepository repository;

	// 找出指定圖片 沒有則回傳預設圖片
	@Override
	public byte[] findImg(final int id) {
		final Optional<TicketImage> optional = repository.findById(id);

		// 沒有指定編號的圖片
		if (optional.isEmpty()) {
			final ClassPathResource resource = new ClassPathResource("images/aa.gif");

			byte[] bytes = null;
			try (final InputStream is = resource.getInputStream();) {
				bytes = new byte[is.available()];
				is.read(bytes);

			} catch (final IOException e) {
				e.printStackTrace();
			}
			return bytes;
		}

		return optional.get().getImage();
	}

	// 找出指定票券的所有圖片URL
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

	// 找出指定票券的圖片URL
	@Override
	public String findImgUrl(final int ticketId) {
		final List<Integer> arrays = repository.findIdsByTicketId(ticketId);

		if (arrays.isEmpty()) {
			return IMG_URL + 0;
		}
		return IMG_URL + arrays.get(0);
	}

}
