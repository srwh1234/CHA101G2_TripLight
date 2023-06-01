package com.tw.ticket.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketComment;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Integer> {

	// 取得指定票券編號的評論
	public Page<TicketComment> findAllByTicketId(final int ticketId, Pageable pageable);
}
