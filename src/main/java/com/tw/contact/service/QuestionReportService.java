package com.tw.contact.service;

import com.tw.contact.QuestionReportRequestDTO;
import com.tw.contact.modelJPA.QuestionReport;

import java.util.List;

public interface QuestionReportService {

    public void createQuestionReport(QuestionReportRequestDTO questionReportRequestDTO);
    public void save(QuestionReport questReport);
    public List<QuestionReport> showQuestReportById(int memberId);
    public void deleteQuestionReport(int id);
    public void updateScore(int id, int score);
}
