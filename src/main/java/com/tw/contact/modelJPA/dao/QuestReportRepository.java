package com.tw.contact.modelJPA.dao;

import com.tw.contact.modelJPA.QuestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestReportRepository extends JpaRepository<QuestReport, Integer> {
    List<QuestReport> findByMemberMemberId(int memberId);

}
