package com.tw.ticket.service;

import org.springframework.web.multipart.MultipartFile;

public interface BkImageService {

	/**
	 * 新增多張圖片
	 *
	 * @param ticketId
	 * @param files
	 */
	public void addImages(int ticketId, final MultipartFile[] files);

	/**
	 * 新增圖片
	 *
	 * @param ticketId
	 * @param file
	 * @return
	 */
	public String addImage(int ticketId, final MultipartFile file);

	/**
	 * 移除圖片
	 *
	 * @param imageId
	 * @return
	 */
	public boolean removeImage(final int imageId);
}
