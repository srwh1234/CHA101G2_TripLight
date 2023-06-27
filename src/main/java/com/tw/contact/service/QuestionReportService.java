package com.tw.contact.service;

import com.tw.contact.controller.dto.QuestionReportRequestDTO;
import com.tw.contact.model.QuestionReport;

import java.util.List;

public interface QuestionReportService {

    /*
    *
    * 前台的處理
    * */
    void createQuestionReport(QuestionReportRequestDTO questionReportRequestDTO);
    void save(QuestionReport questionReport);
    List<QuestionReport> showQuestionReportById(int memberId);
    void deleteQuestionReport(int id);
    void updateScore(int id, int score);
    QuestionReport checkQuestionDetail(int id);

    /*
    *
    * 後臺處理
    * */

    //顯示未處理的問題給員工處理
    List<QuestionReport> showQuestionReportByState(int state);
    void updateQuestionReport(int id, int employeeId, String replyContent);
}
