package com.tw.ticket.service;

import java.util.Map;

import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.BkTicketController.TikcetDto;
import com.tw.ticket.controller.TicketController.SearchRequest;

public interface BkTicketService {

	// 後台票券清單 (分頁)
	public SearchResponse getItems(SearchRequest request);

	// 後台新增票券
	public boolean addItems(TikcetDto dto);

	// 後台上下架票券
	public boolean enableItems(Map<String, Object> map);

	// 後台增加票券數量
	public boolean addItemCount(Map<String, Object> map);

	// 後台取得要編輯的票券
	public TikcetDto getItem(int ticketId);

	// 後台編輯票券
	public boolean updateItem(TikcetDto dto);

}
