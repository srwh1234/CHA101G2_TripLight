package com.tw.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.DetailController.CommentResponse;
import com.tw.ticket.controller.DetailController.SearchRequest;
import com.tw.ticket.dao.TicketCommentRepository;
import com.tw.ticket.model.TicketComment;
import com.tw.ticket.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private TicketCommentRepository repository;

	@Override
	public CommentResponse getItems(final SearchRequest searchRequest) {
		final Pageable pageable = PageRequest.of(//
				searchRequest.getPage(), 	// 第幾頁
				searchRequest.getSize()		// 一頁幾個
		);

		final Page<TicketComment> page = repository.findAllByTicketId(searchRequest.getTicketId(), pageable);

		// 填寫回應
		final CommentResponse commentResponse = new CommentResponse();
		commentResponse.setCurPage(searchRequest.getPage());
		commentResponse.setCurPage(page.getTotalPages());
		page.getContent().forEach(comment -> {
			commentResponse.getComments().add(comment);
		});
		return commentResponse;
	}

}
