package com.tw.vendor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.Trip;


@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {}
