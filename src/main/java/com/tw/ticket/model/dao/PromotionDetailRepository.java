package com.tw.ticket.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.PromotionDetail;
import com.tw.ticket.model.PromotionDetail.PrimaryKey;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, PrimaryKey> {

	@Query("SELECT pd FROM PromotionDetail pd JOIN Promotion p "//
			+ "ON pd.key.promotionId = p.promotionId "	//
			+ "WHERE pd.key.ticketId = :id "			//
			+ "AND CURRENT_TIMESTAMP >= p.startDate "	//
			+ "AND CURRENT_TIMESTAMP <= endDate")//
	public List<PromotionDetail> findUsableByTicketId(@Param("id") int ticketId);

	public void deleteByKeyPromotionId(int promotionId);
}
