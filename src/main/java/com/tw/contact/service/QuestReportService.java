package com.tw.contact.service;

import com.tw.contact.modelJPA.QuestReport;

import java.util.List;

public interface QuestReportService {
    public void save(QuestReport questReport);

    public List<QuestReport> showQuestReport();

    public List<QuestReport> showQuestReportById(int memberId);

//    public QuestReport findById(int i);
    public void deleteQuestReport(int i);
}
