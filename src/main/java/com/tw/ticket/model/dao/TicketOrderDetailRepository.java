package com.tw.ticket.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketOrderDetail;

@Repository
public interface TicketOrderDetailRepository extends JpaRepository<TicketOrderDetail, Integer> {

	// 取得指定的訂單明細
	@Query("SELECT d FROM TicketOrderDetail d " //
			+ "WHERE d.ticketOrderId=:id AND d.ticketSn.ticketSnId=:snid")
	public TicketOrderDetail findByKeyId(@Param("id") int ticketOrderId, @Param("snid") int ticketSnId);

	// 取得指定訂單編號的所有明細
	public List<TicketOrderDetail> findByTicketOrderId(int ticketOrderId);

	// 取得指定的訂單明細
	public TicketOrderDetail findByTicketOrderIdAndTicketSnTicketSnId(int ticketOrderId, int ticketSnId);
}
