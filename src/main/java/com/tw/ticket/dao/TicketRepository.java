package com.tw.ticket.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	// 指定城市的所有票券
	public List<Ticket> findByCityContaining(String city);

	// 按照銷售量排序
	public List<Ticket> findAllByOrderByTotalSalesDesc();

	// 關鍵字搜尋 hql
	@Query("SELECT t FROM Ticket t WHERE t.name LIKE %:keyword% AND t.city IN :cities AND t.ticketTypeId IN :types")
	public Page<Ticket> searchTicketByKeyword(	//
			@Param("keyword") String keyword,	// 關鍵字
			@Param("types") int[] types, 		// 類型
			@Param("cities") String[] cities,	// 縣市
			Pageable pageable);
}
