package com.tw.ticket.service;

import java.util.List;

public interface ImageService {

	// 找出指定圖片
	public byte[] findImg(final int id);

	// 找出指定票券的所有圖片URL
	public List<String> findImgUrls(int ticketId);

	// 找出指定票券的圖片URL
	public String findImgUrl(int ticketId);
}
