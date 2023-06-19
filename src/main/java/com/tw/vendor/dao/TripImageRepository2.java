package com.tw.vendor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.TripImage2;

@Repository
public interface TripImageRepository2 extends JpaRepository<TripImage2, Integer> {
	
}