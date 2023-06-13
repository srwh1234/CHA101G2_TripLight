package com.tw.contact.controller;

import com.tw.contact.Dto;
import com.tw.contact.modelJPA.QuestReport;
import com.tw.contact.service.QuestReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestReportController {

    private QuestReportService questReportService;

    @Autowired
    public QuestReportController(QuestReportService questReportService) {
        this.questReportService = questReportService;
    }

    @PostMapping("/createQuestReport")
    public QuestReport save(@RequestBody Dto dto){
        QuestReport questReport = new QuestReport();
        questReport.setQContent(dto.getqContent());
        return questReportService.save(questReport);
    }
}
