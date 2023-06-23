package com.tw.ticket.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.TicketController.DescTicketDto;
import com.tw.ticket.controller.TicketController.PageDto;
import com.tw.ticket.controller.TicketController.PageReqDto;
import com.tw.ticket.controller.TicketDetailController.DetailDto;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketFavorite.PrimaryKey;
import com.tw.ticket.model.dao.TicketFavoriteRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.service.ImageService;
import com.tw.ticket.service.PromotionService;
import com.tw.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private TicketFavoriteRepository favoriteRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private PromotionService promotionService;

	/**
	 * 取得票券明細
	 *
	 * @param memberId 會員編號 (取得我的最愛會用到)
	 * @param ticketId 票券編號
	 * @return
	 */
	@Override
	public DetailDto getItem(final int memberId, final int ticketId) {
		final Ticket ticket = repository.findById(ticketId).orElse(null);

		if (ticket == null) {
			return null;
		}
		final DetailDto detailDto = new DetailDto(ticket);

		// 圖片
		detailDto.setImages(imageService.findImgUrls(ticket.getTicketId()));

		// 可用數量
		detailDto.setAvailable(snRepository.countUsableSn(ticketId));

		// 促銷
		detailDto.setPromotion(promotionService.getItem(ticketId));

		// 我的最愛
		detailDto.setFavorite(favoriteRepository.existsById(new PrimaryKey(memberId, ticket)));
		return detailDto;
	}

	/**
	 * 隨機票券 (隨機選4個)
	 *
	 * @return
	 */
	@Override
	public List<DescTicketDto> getRandomItem() {
		final List<DescTicketDto> result = new ArrayList<>();

		final List<Ticket> tickets = repository.findAll();

		// 打亂
		Collections.shuffle(tickets);

		for (final Ticket ticket : tickets) {
			if (result.size() >= 4) {
				break;
			}
			if (ticket.getStatus() != Ticket.ENABLED) {
				continue;
			}
			final DescTicketDto descDto = new DescTicketDto(ticket);
			descDto.setImage(imageService.findImgUrl(ticket.getTicketId()));
			descDto.setPromotion(promotionService.getItem(ticket.getTicketId()));
			result.add(descDto);
		}
		return result;
	}

	/**
	 * 熱門票券 (按照銷售量選8個)
	 *
	 * @return
	 */
	@Override
	public List<DescTicketDto> getHotItem() {
		final List<DescTicketDto> result = new ArrayList<>();

		for (final Ticket ticket : repository.findAllByOrderByTotalSalesDesc()) {
			if (result.size() >= 8) {
				break;
			}
			if (ticket.getStatus() != Ticket.ENABLED) {
				continue;
			}
			final DescTicketDto descDto = new DescTicketDto(ticket);
			descDto.setImage(imageService.findImgUrl(ticket.getTicketId()));
			descDto.setPromotion(promotionService.getItem(ticket.getTicketId()));
			result.add(descDto);
		}
		return result;
	}

	/**
	 * 搜尋票券
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	@Override
	public PageDto getSearchItem(final PageReqDto request) {
		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);

		final Page<Ticket> page = repository.searchTicketByKeywords(	//
				request.getKeyword(),	// 關鍵字
				request.getTypes(),		// 類型
				request.getCities(),	// 縣市
				pageable				// 分頁物件
		);

		// 轉成自己定義的物件
		final PageDto pageDto = new PageDto();
		pageDto.setCurPage(request.getPage());
		pageDto.setTotalPage(page.getTotalPages());

		for (final Ticket ticket : page.getContent()) {
			final DescTicketDto descDto = new DescTicketDto(ticket);
			descDto.setImage(imageService.findImgUrl(ticket.getTicketId()));
			descDto.setPromotion(promotionService.getItem(ticket.getTicketId()));
			pageDto.getTickets().add(descDto);
		}
		return pageDto;
	}

	/**
	 * 未促銷的票券清單
	 *
	 * @return
	 */
	@Override
	public List<Ticket> getItemsWithoutPromote() {
		return repository.searchTicketWithoutPromote();
	}

	/**
	 * 輸入地點取得票券 (AI 行程，地點搜尋票券)
	 *
	 * @param destination 目的地的名稱
	 * @return
	 */
	@Override
	public List<DescTicketDto> getTicket(final String destination) {
		final List<DescTicketDto> result = new ArrayList<>();

		repository.findByCityContaining(destination).forEach(ticket -> {
			final DescTicketDto descDto = new DescTicketDto(ticket);
			final String imageUrl = imageService.findImgUrl(ticket.getTicketId());
			descDto.setImage(imageUrl);
			result.add(descDto);
		});
		return result;
	}

}
