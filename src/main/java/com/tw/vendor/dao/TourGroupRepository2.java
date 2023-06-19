package com.tw.vendor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.TourGroup2;

@Repository
public interface TourGroupRepository2 extends JpaRepository<TourGroup2, Integer> {

}
