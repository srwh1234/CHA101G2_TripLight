package com.tw.ticket.service.impl;

import static com.tw.ticket.model.Ticket.ENABLED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.TicketController.DescResponse;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.controller.TicketController.SearchResponse;
import com.tw.ticket.controller.TicketDetailController.DetailResponse;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketFavorite.PrimaryKey;
import com.tw.ticket.model.dao.TicketFavoriteRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.service.ImageService;
import com.tw.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private TicketFavoriteRepository ticketFavoriteRepository;

	// 取得票券明細
	@Override
	public DetailResponse getItem(final int memberId, final int ticketId) {
		final Ticket ticket = repository.findById(ticketId).orElse(null);

		if (ticket == null) {
			return null;
		}
		final DetailResponse detailResponse = new DetailResponse(ticket);
		final List<String> images = imageService.findImgUrls(ticket.getTicketId());
		detailResponse.setImages(images);

		// 我的最愛
		final boolean isFavorite = //
				ticketFavoriteRepository.existsById(new PrimaryKey(memberId, ticket));
		detailResponse.setFavorite(isFavorite);

		// 可用數量
		detailResponse.setAvailable(snRepository.countUsableSn(ticketId));
		return detailResponse;
	}

	// 隨機票券
	@Override
	public List<DescResponse> getRandomItem() {
		final List<DescResponse> result = new ArrayList<>();

		final List<Ticket> tickets = repository.findAll();

		// 打亂
		Collections.shuffle(tickets);

		for (final Ticket ticket : tickets) {
			if (result.size() >= 4) {
				break;
			}
			if (ticket.getStatus() == ENABLED) {
				final DescResponse response = new DescResponse(ticket);
				final String imageUrl = imageService.findImgUrl(ticket.getTicketId());
				response.setImage(imageUrl);
				result.add(response);
			}
		}
		return result;
	}

	// 熱門票券
	@Override
	public List<DescResponse> getHotItem() {
		final List<DescResponse> result = new ArrayList<>();

		repository.findAllByOrderByTotalSalesDesc().forEach(ticket -> {
			if (result.size() >= 8) {
				return;
			}
			if (ticket.getStatus() == ENABLED) {
				final DescResponse response = new DescResponse(ticket);
				final String imageUrl = imageService.findImgUrl(ticket.getTicketId());
				response.setImage(imageUrl);
				result.add(response);
			}

		});
		return result;
	}

	// 搜尋票券
	@Override
	public SearchResponse getSearchItem(final SearchRequest request) {
		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);

		final Page<Ticket> page = repository.searchTicketByKeyword(	//
				request.getKeyword(),	// 關鍵字
				request.getTypes(),		// 類型
				request.getCities(),	// 縣市
				pageable				// 分頁物件
		);

		// 轉成自己定義的物件
		final SearchResponse response = new SearchResponse();
		response.setCurPage(request.getPage());
		response.setTotalPage(page.getTotalPages());

		page.getContent().forEach(ticket -> {
			final DescResponse descResponse = new DescResponse(ticket);
			final String imageUrl = imageService.findImgUrl(ticket.getTicketId());
			descResponse.setImage(imageUrl);
			response.getTickets().add(descResponse);
		});

		return response;
	}

	// AI 行程，地點搜尋票券
	@Override
	public List<DescResponse> getTicket(final String destination) {
		final List<DescResponse> result = new ArrayList<>();

		repository.findByCityContaining(destination).forEach(ticket -> {
			final DescResponse descResponse = new DescResponse(ticket);
			final String imageUrl = imageService.findImgUrl(ticket.getTicketId());
			descResponse.setImage(imageUrl);
			result.add(descResponse);
		});
		return result;
	}

}
