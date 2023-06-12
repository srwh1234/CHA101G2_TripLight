package com.tw.contact.modelJPA.dao;

import com.tw.contact.modelJPA.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FaqDAO extends JpaRepository<Faq, Integer> {
}
