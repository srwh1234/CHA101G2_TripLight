package com.tw.ticket.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.MyUtils;
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
			byte[] bytes = null;
			InputStream is = null;
			ByteArrayOutputStream os = null;

			try {
				is = this.getClass().getResourceAsStream("/images/aa.gif");
				os = new ByteArrayOutputStream();

				/*
				 使用 is.available()讀取檔案長度
				 在jar檔裡可能會有些問題


				 還是用原本的 while read 靠譜一點
				 * */
				int read;
				final byte[] buf = new byte[4096];

				while ((read = is.read(buf, 0, buf.length)) != -1) {
					os.write(buf, 0, read);
				}
				bytes = os.toByteArray();

			} catch (final IOException e) {
				log.error(e.getLocalizedMessage(), e);

			} finally {
				MyUtils.close(os);
				MyUtils.close(is);
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
