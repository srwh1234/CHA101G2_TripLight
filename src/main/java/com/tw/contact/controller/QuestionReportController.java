package com.tw.contact.controller;

import com.tw.contact.QuestionReportRequestDTO;
import com.tw.contact.modelJPA.QuestionReport;
import com.tw.contact.service.QuestionReportService;
import com.tw.member.model.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class QuestionReportController {

    private final QuestionReportService questionReportService;

    @Autowired
    QuestionReportController(QuestionReportService questionReportService) {
        this.questionReportService = questionReportService;
    }


    //會員送出問題
    @PostMapping(value = "/createQuestionReport")
    public ResponseEntity<String> save(@RequestBody QuestionReportRequestDTO questionReportRequestDTO){
        questionReportService.createQuestionReport(questionReportRequestDTO);
        return ResponseEntity.ok().body("{\"message\": \"問題已送出!\"}");
    }
    @PostMapping(value ="/questionReports/{id}/score")
    public void updateScore(@PathVariable int id, @RequestBody Map<String, Integer> scoreMap){
        int score = scoreMap.get("score");
        questionReportService.updateScore(id, score);
    }

    //會員查自己的問題回報紀錄
    @GetMapping(value = "/question_report")
    public List<QuestionReport>  showMemberQuestionList(@RequestParam("member") int memberId){
        return questionReportService.showQuestReportById(memberId);
    }

    //會員刪除特定id的問題
    @DeleteMapping(value = "/question_report/{id}")
    public void deleteQuestionReport(@PathVariable int id) {
        questionReportService.deleteQuestionReport(id);
    }
}
