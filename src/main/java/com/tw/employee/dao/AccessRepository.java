package com.tw.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.employee.model.Access;

@Repository
public interface AccessRepository extends JpaRepository<Access , Integer>{}
