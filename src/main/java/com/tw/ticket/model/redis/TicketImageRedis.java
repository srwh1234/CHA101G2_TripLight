package com.tw.ticket.model.redis;

import java.util.List;

import com.tw.ticket.model.TicketImage;

public interface TicketImageRedis {

	// 指定編號的TicketImage
	public TicketImage findById(int id);

	// 新增全部
	public List<TicketImage> saveAll(List<TicketImage> ticketImages);

	// 新增
	public TicketImage save(TicketImage ticketImage);

	// 刪除
	public void delete(TicketImage ticketImage);

}
