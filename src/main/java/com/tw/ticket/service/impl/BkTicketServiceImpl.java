package com.tw.ticket.service.impl;

import static com.tw.ticket.model.Ticket.DISABLED;
import static com.tw.ticket.model.TicketSn.NOT_USED;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.MyUtils;
import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.BkTicketController.TicketResponse;
import com.tw.ticket.controller.BkTicketController.TikcetRequest;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.TicketSn;
import com.tw.ticket.model.TicketType;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.model.dao.TicketTypeRepository;
import com.tw.ticket.service.BkTicketService;

@Service
public class BkTicketServiceImpl implements BkTicketService {

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private TicketTypeRepository ticketTypeRepository;

	@Autowired
	private TicketImageRepository ticketImageRepository;

	// 後台新增票券
	@Override
	public boolean addItems(final TikcetRequest request) {

		// 如果是0張
		if (request.getAvailable() <= 0) {
			return false;
		}

		// 判斷票券類型
		final TicketType type = ticketTypeRepository.findByName(request.getTicketType());

		if (type == null) {
			return false;
		}

		// 預設為未上架
		final Ticket ticket = new Ticket();

		ticket.setName(request.getName());
		ticket.setTicketType(type);
		ticket.setStatus(DISABLED);
		ticket.setPrice(request.getPrice());
		ticket.setTotalSales(request.getTotalSales());
		ticket.setExpiryDate(request.getExpiryDate());
		ticket.setDescription(request.getDescription());
		ticket.setContent(request.getContent());
		ticket.setNote(request.getNote());
		ticket.setSupplierName(request.getSupplierName());
		ticket.setCity(request.getCity());
		ticket.setAddress(request.getAddress());
		ticket.setLatitude(request.getLatitude());
		ticket.setLongitude(request.getLongitude());
		ticket.setRatingSum(request.getRating() * request.getRatingPerson());
		ticket.setRatingCount(request.getRatingPerson());

		// 價格
		if (ticket.getPrice() <= 0) {
			return false;
		}
		// 到期日
		if (MyUtils.isBeforeNow(ticket.getExpiryDate())) {
			return false;
		}

		// 預設評價
		if (ticket.getRatingSum() <= 0 || ticket.getRatingCount() <= 0) {
			return false;
		}

		// 空字串判斷
		if (MyUtils.isEmpty(ticket.getName())				// 名稱
				|| MyUtils.isEmpty(ticket.getDescription()) // 票券描述
				|| MyUtils.isEmpty(ticket.getContent()) 	// 票券說明
				|| MyUtils.isEmpty(ticket.getNote()) 		// 注意事項
				|| MyUtils.isEmpty(ticket.getSupplierName())// 供應商名稱
				|| MyUtils.isEmpty(ticket.getCity()) 		// 縣市
				|| MyUtils.isEmpty(ticket.getAddress())) { 	// 地址
			return false;
		}
		// 經緯度
		if (ticket.getLatitude() <= 0 || ticket.getLongitude() <= 0) {
			ticket.setLatitude(24.9576355);
			ticket.setLongitude(121.2250227);
		}

		// 新增票券
		repository.save(ticket);

		// 隨機序號
		final List<TicketSn> ticketSns = new ArrayList<>();
		for (int i = 0; i < request.getAvailable(); i++) {
			final TicketSn ticketSn = new TicketSn();
			ticketSn.setTicket(ticket);

			final String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
			ticketSn.setSerialNumber(uuId);
			ticketSn.setStatus(NOT_USED);
			ticketSns.add(ticketSn);
		}
		snRepository.saveAll(ticketSns);

		// 圖片
		final List<TicketImage> ticketImages = new ArrayList<>();
		for (final String base64Str : request.getImages()) {
			final byte[] array = Base64.getDecoder().decode(base64Str);

			final TicketImage image = new TicketImage();
			image.setTicketId(ticket.getTicketId());
			image.setImage(array);
			image.setUploadTime(new Timestamp(System.currentTimeMillis()));
			ticketImages.add(image);
		}
		ticketImageRepository.saveAll(ticketImages);

		return true;
	}

	@Override
	public SearchResponse getItems(final SearchRequest request) {
		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);
		final Page<Ticket> page = repository.searchTicketByKeyword(	//
				request.getKeyword(),	// 關鍵字
				pageable				// 分頁物件
		);
		// 轉成自己定義的物件
		final SearchResponse response = new SearchResponse();
		response.setCurPage(request.getPage());
		response.setTotalPage(page.getTotalPages());

		page.getContent().forEach(ticket -> {

			final int available = snRepository.countUsableSn(ticket.getTicketId());

			final TicketResponse ticketResponse = new TicketResponse();
			ticketResponse.setTicketId(ticket.getTicketId());
			ticketResponse.setTicketName(ticket.getName());
			ticketResponse.setTicketType(ticket.getTicketType().getName());
			ticketResponse.setTicketCity(ticket.getCity());
			ticketResponse.setSupplierName(ticket.getSupplierName());
			ticketResponse.setAvailable(available);
			response.getTickets().add(ticketResponse);

		});
		return response;
	}

}
