package com.tw.ticket.model.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	// 指定城市的所有票券
	public List<Ticket> findByCityContaining(String city);

	// 按照銷售量排序
	public List<Ticket> findAllByOrderByTotalSalesDesc();

	// 前台關鍵字搜尋
	@Query("SELECT t FROM Ticket t WHERE t.name LIKE %:keyword% AND status=1"//
			+ " AND t.city IN :cities AND t.ticketType.name IN :types ORDER BY t.ticketId")
	public Page<Ticket> searchTicketByKeywords(	//
			@Param("keyword") String keyword,	// 關鍵字
			@Param("types") String[] types, 	// 類型
			@Param("cities") String[] cities,	// 縣市
			Pageable pageable					// 分頁
	);

	@Query("SELECT t FROM Ticket t WHERE t.name LIKE %:keyword%")
	public Page<Ticket> searchTicketByKeyword(	//
			@Param("keyword") String keyword,	// 關鍵字
			Pageable pageable					// 分頁
	);

	// 找出符合編號陣列的票券
	public List<Ticket> findByTicketIdIn(Collection<Integer> array);

	// 沒有促銷的票券
	@Query("SELECT t FROM Ticket t WHERE t.ticketId NOT IN (SELECT DISTINCT p.key.ticketId FROM PromotionDetail p)")
	public List<Ticket> searchTicketWithoutPromote();

}
