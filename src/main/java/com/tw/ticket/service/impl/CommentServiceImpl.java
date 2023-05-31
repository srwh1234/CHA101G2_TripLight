package com.tw.ticket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.ticket.controller.DetailController.CommentResponse;
import com.tw.ticket.dao.TicketCommentRepository;
import com.tw.ticket.model.TicketComment;
import com.tw.ticket.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private TicketCommentRepository repository;

	@Override
	public CommentResponse getComment(final long ticketId) {
		final List<TicketComment> list = repository.findAllByTicketId(ticketId);

		list.forEach(t -> {
			System.out.println(t.getComment());
		});
		return null;
	}

}
