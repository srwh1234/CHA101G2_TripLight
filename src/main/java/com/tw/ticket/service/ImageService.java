package com.tw.ticket.service;

import java.util.List;

public interface ImageService {

	// XXX http://localhost:8080 VsCode測試才要加

	public static String IMG_URL = "http://localhost:8080/img/";// 圖片的URL

	/**
	 * 找出指定圖片 沒有則回傳預設圖
	 *
	 * @param id 圖票編號
	 * @return
	 */
	public byte[] findImg(final int id);

	/**
	 * 找出指定票券的所有圖片URL
	 *
	 * @param ticketId 票券編號
	 * @return
	 */
	public List<String> findImgUrls(int ticketId);

	/**
	 * 找出指定票券的圖片URL
	 *
	 * @param ticketId 票券編號
	 * @return
	 */
	public String findImgUrl(int ticketId);
}
