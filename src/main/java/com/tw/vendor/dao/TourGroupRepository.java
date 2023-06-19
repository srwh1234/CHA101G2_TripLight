package com.tw.vendor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.TourGroup;

@Repository
public interface TourGroupRepository extends JpaRepository<TourGroup, Integer> {

}
