package com.tw.ticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketSn;

@Repository
public interface TicketSnRepository extends JpaRepository<TicketSn, Integer> {

	// 關鍵字搜尋 hql
	@Query("SELECT t FROM TicketSn t WHERE t.ticket.ticketId=:id AND t.status=0")
	public List<TicketSn> searchUsableSn(@Param("id") int id);

	@Query("SELECT COUNT(t) FROM TicketSn t WHERE t.ticket.ticketId=:id AND t.status=0")
	public int countUsableSn(@Param("id") int id);
}
