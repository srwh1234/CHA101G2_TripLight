package com.tw.ticket.service;

import org.springframework.web.multipart.MultipartFile;

public interface BkImageService {

	// 後台新增圖片集合
	public void addImages(int ticketId, final MultipartFile[] files);

	// 後台新增圖片
	public String addImage(int ticketId, final MultipartFile file);

	// 後台移除圖片
	public boolean removeImage(final int employeeId, final int imageId);
}
