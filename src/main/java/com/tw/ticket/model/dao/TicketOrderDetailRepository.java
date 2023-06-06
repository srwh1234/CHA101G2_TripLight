package com.tw.ticket.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.TicketOrderDetail;
import com.tw.ticket.model.TicketOrderDetail.PrimaryKey;

@Repository
public interface TicketOrderDetailRepository extends JpaRepository<TicketOrderDetail, PrimaryKey> {

	// 取得指定的訂單明細
	@Query("SELECT d FROM TicketOrderDetail d " //
			+ "WHERE d.key.ticketOrderId=:id AND d.key.ticketSn.ticketSnId=:snid")
	public TicketOrderDetail findByKeyId(@Param("id") int ticketOrderId, @Param("snid") int ticketSnId);
}
