package com.tw.contact.modelJPA.dao;


import com.tw.contact.modelJPA.QuestionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestionReportRepository extends JpaRepository<QuestionReport, Integer> {
    List<QuestionReport> findByMemberMemberId(int memberId);
    List<QuestionReport> findByState(int state);
}
