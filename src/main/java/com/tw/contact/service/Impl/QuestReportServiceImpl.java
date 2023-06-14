package com.tw.contact.service.Impl;

import com.tw.contact.modelJPA.QuestReport;
import com.tw.contact.modelJPA.dao.QuestReportRepository;
import com.tw.contact.service.QuestReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class QuestReportServiceImpl implements QuestReportService {
    private QuestReportRepository questReportRepository;

    @Autowired
    public QuestReportServiceImpl(QuestReportRepository questReportRepository) {
        this.questReportRepository = questReportRepository;
    }

    @Override
    public void save(QuestReport questReport) {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        questReport.setStartTime(LocalDateTime.now(zoneId));
        questReportRepository.save(questReport);
    }

    @Override
    public List<QuestReport> showQuestReport(){
        return questReportRepository.findAll();
    }

    @Override
    public List<QuestReport> showQuestReportById(int memberId) {
        return questReportRepository.findByMemberMemberId(memberId);
    }


}
