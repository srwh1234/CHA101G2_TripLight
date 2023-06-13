package com.tw.vendor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.Trip2;

// @Table(name="Trip")
@Repository
public interface TripRepository2 extends JpaRepository<Trip2, Integer> {
}
