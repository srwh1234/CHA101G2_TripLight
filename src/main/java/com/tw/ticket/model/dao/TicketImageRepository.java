package com.tw.ticket.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketImage;

@Repository
public interface TicketImageRepository extends JpaRepository<TicketImage, Integer> {

	// 找出指定票券編號的圖片編號清單
	@Query("SELECT t.id FROM TicketImage t WHERE t.ticketId=:id")
	public List<Integer> findIdsByTicketId(@Param("id") int ticketId);
}
