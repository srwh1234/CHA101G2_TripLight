package com.tw.contact.modelJPA.dao;

import com.tw.contact.modelJPA.ChatRecord;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public interface ChatRecordDAO extends JpaRepository<ChatRecord, Integer> {


}
