package com.tw.ticket.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.tw.ticket.controller.BkTicketController.PageDto;
import com.tw.ticket.controller.BkTicketController.TikcetDto;
import com.tw.ticket.controller.TicketController.PageReqDto;

public interface BkTicketService {

	/**
	 * @param reqDto 請求參數
	 * @return 後台票券清單分頁
	 */
	public PageDto getItems(PageReqDto reqDto);

	/**
	 * @param ticketId
	 * @return 後台要編輯的票券資料
	 */
	public TikcetDto getItem(int ticketId);

	/**
	 * @param map 請求參數
	 * @return 後台上下架票券
	 */
	public boolean enableItems(Map<String, Object> map);

	/**
	 * @param map 請求參數
	 * @return 後台增加票券數量
	 */
	public boolean addItemCount(Map<String, Object> map);

	/**
	 * @param jsonString
	 * @param files
	 * @return 後台新增票券
	 */
	public boolean addItems(final String jsonString, MultipartFile[] files);

	/**
	 * @param dto 請求參數
	 * @return 後台編輯票券
	 */
	public boolean updateItem(TikcetDto dto);

}
