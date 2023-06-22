package com.tw.ticket.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.controller.FavoriteController.FavoriteReqDto;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketFavorite;
import com.tw.ticket.model.TicketFavorite.PrimaryKey;
import com.tw.ticket.model.dao.TicketFavoriteRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketFavoriteRepository ticketFavoriteRepository;

	/**
	 * 更新我的最愛的狀態
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	@Override
	public int updateItem(final FavoriteReqDto reqDto) {
		final Member member = memberRepository.findById(reqDto.getMemberId()).orElse(null);
		if (member == null) {
			return FAVORITE_LOGIN;
		}

		final Ticket ticket = ticketRepository.findById(reqDto.getTicketId()).orElse(null);

		if (ticket == null) {
			return FAVORITE_ERROR;
		}

		// 如果是已經收藏的 就取消收藏
		if (reqDto.isFavorite()) {
			ticketFavoriteRepository.deleteById(new PrimaryKey(reqDto.getMemberId(), ticket));
			return FAVORITE_DEL_OK;
		}
		final TicketFavorite favorite = new TicketFavorite();
		favorite.setKey(new PrimaryKey(reqDto.getMemberId(), ticket));
		favorite.setAddTime(new Timestamp(System.currentTimeMillis()));
		ticketFavoriteRepository.save(favorite);

		return FAVORITE_ADD_OK;
	}
}
