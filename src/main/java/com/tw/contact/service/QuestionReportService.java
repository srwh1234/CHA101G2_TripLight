package com.tw.contact.service;

import com.tw.contact.QuestionReportRequestDTO;
import com.tw.contact.modelJPA.QuestionReport;

import java.util.List;

public interface QuestionReportService {

    void createQuestionReport(QuestionReportRequestDTO questionReportRequestDTO);
    void save(QuestionReport questReport);
    List<QuestionReport> showQuestReportById(int memberId);
    void deleteQuestionReport(int id);
    void updateScore(int id, int score);
    QuestionReport checkQuestionDetail(int id);
}
