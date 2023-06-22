package com.tw.ticket.service;

import java.util.List;
import java.util.Map;

import com.tw.ticket.controller.BkOrderController.DetailDto;
import com.tw.ticket.controller.BkOrderController.PageDto;
import com.tw.ticket.controller.BkOrderController.PageReqDto;

public interface BkOrderService {

	/**
	 * @param reqDto 請求參數
	 * @return 會員票券訂單分頁
	 */
	public PageDto getItems(PageReqDto reqDto);

	/**
	 * @param orderId 訂單編號
	 * @return 會員票券訂單明細
	 */
	public List<DetailDto> getDetailItems(int orderId);

	/**
	 * 變更票卷訂單明細退貨狀態
	 *
	 * @param map 請求參數
	 * @return
	 */
	public boolean updateItem(final Map<String, Object> map);
}
