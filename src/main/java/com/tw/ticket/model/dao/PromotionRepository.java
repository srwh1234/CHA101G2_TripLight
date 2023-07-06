package com.tw.ticket.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.ticket.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	//
}
