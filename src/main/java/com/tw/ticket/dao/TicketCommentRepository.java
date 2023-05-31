package com.tw.ticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketComment;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {
	// 取得指定票券編號的評論
	public List<TicketComment> findAllByTicketId(final long ticketId);
}
