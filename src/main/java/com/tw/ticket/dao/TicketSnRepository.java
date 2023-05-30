package com.tw.ticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketSn;

@Repository
public interface TicketSnRepository extends JpaRepository<TicketSn, Long> {

	// 關鍵字搜尋 hql
	@Query("SELECT t FROM TicketSn t WHERE t.ticket.ticketId =:id AND t.status=0")
	public List<TicketSn> searchUsableSn(@Param("id") long id);
}
