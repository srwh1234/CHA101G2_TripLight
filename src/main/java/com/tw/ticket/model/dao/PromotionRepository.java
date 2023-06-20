package com.tw.ticket.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.tw.ticket.model.Promotion;

@CrossOrigin(origins = "*")
@RepositoryRestResource
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	//
}
