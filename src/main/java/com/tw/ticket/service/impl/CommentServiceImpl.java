package com.tw.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.TicketDetailController.CommentDto;
import com.tw.ticket.controller.TicketDetailController.PageReqDto;
import com.tw.ticket.model.TicketComment;
import com.tw.ticket.model.dao.TicketCommentRepository;
import com.tw.ticket.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private TicketCommentRepository repository;

	/**
	 * 取得票券評論
	 *
	 * @param reqDto 請求參數
	 * @return
	 */
	@Override
	public CommentDto getItems(final PageReqDto reqDto) {
		final Pageable pageable = PageRequest.of(//
				reqDto.getPage(), 		// 第幾頁
				reqDto.getSize()		// 一頁幾個
		);

		final Page<TicketComment> page = repository.findAllByTicketId(reqDto.getTicketId(), pageable);

		// 填寫回應
		final CommentDto commentDto = new CommentDto();
		commentDto.setCurPage(reqDto.getPage());
		commentDto.setCurPage(page.getTotalPages());

		page.getContent().forEach(comment -> {
			commentDto.getComments().add(comment);
		});
		return commentDto;
	}

}
