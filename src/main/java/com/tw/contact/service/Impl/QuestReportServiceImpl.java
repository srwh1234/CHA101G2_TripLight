package com.tw.contact.service.Impl;

import com.tw.contact.modelJPA.QuestReport;
import com.tw.contact.modelJPA.dao.QuestReportRepository;
import com.tw.contact.service.QuestReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class QuestReportServiceImpl implements QuestReportService {
    private QuestReportRepository questReportRepository;

    @Autowired
    public QuestReportServiceImpl(QuestReportRepository questReportRepository) {
        this.questReportRepository = questReportRepository;
    }

    @Override
    public QuestReport save(QuestReport questReport) {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        questReport.setStartTime(LocalDateTime.now(zoneId));
        return questReportRepository.save(questReport);
    }
}
