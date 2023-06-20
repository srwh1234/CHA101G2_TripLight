package com.tw.ticket.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.tw.ticket.model.Promotion;

@CrossOrigin(origins = "*")
@RepositoryRestResource
public interface PromotionRepository extends CrudRepository<Promotion, Integer> {
	//
}
