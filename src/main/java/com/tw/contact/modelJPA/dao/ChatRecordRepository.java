package com.tw.contact.modelJPA.dao;

import com.tw.contact.modelJPA.ChatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRecordRepository extends JpaRepository<ChatRecord, Integer> {

    List<ChatRecord> findByMemberId(int memberId);
}
