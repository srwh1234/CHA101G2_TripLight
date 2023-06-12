package com.tw.contact.modelJPA.dao;

import com.tw.contact.modelJPA.FaqType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FaqTypeDAO extends JpaRepository<FaqType, Integer> {
}
