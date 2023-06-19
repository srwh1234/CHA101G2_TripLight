package com.tw.vendor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.vendor.model.VendorImage;

@Repository
public interface VendorImageRepository extends JpaRepository<VendorImage, Integer> {

}