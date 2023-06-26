package com.tw.contact.controller;

import com.tw.contact.dto.QuestionReportRequestDTO;
import com.tw.contact.model.QuestionReport;
import com.tw.contact.service.QuestionReportService;
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

    /*
    *
    * 前台處理
    *
     */

    //會員送出問題
    @PostMapping(value = "/createQuestionReport")
    public ResponseEntity<String> save(@RequestBody QuestionReportRequestDTO questionReportRequestDTO){
        questionReportService.createQuestionReport(questionReportRequestDTO);
        return ResponseEntity.ok().body("{\"message\": \"問題已送出!\"}");
    }
    //接收會員的評分
    @PostMapping(value ="/questionReports/{id}/score")
    public ResponseEntity<String> updateScore(@PathVariable int id, @RequestBody Map<String, Integer> scoreMap){
        questionReportService.updateScore(id, scoreMap.get("score"));
        return ResponseEntity.ok().body("{\"message\": \"評分已送出!\"}");
    }

    //會員查自己的問題回報紀錄
    @GetMapping(value = "/question_report")
    public List<QuestionReport> showMemberQuestionList(@RequestParam("memberId") int memberId){
        return questionReportService.showQuestionReportById(memberId);
    }

    //會員刪除特定id的問題
    @DeleteMapping(value = "/question_report/{id}")
    public void deleteQuestionReport(@PathVariable int id) {
        questionReportService.deleteQuestionReport(id);
    }

    @GetMapping(value = "/check_question_report_detail")
    public QuestionReport showQuestionDetail(@RequestParam int id){
        return questionReportService.checkQuestionDetail(id);
    }

    
    /*
    後臺處理
     */
    @GetMapping(value = "/question_from_management")
    public List<QuestionReport> showQuestion(){
        return questionReportService.showQuestionReportByState(0);
    }

    @PostMapping("/question_from_management/{id}")
    public ResponseEntity<String> handleQuestionReport(@PathVariable int id, @RequestBody Map<String, Object> payload) {
        String replyContent = payload.get("replyContent").toString(); // 转换为字符串
        int employeeId = Integer.parseInt(payload.get("employeeId").toString()); // 转换为整数
        questionReportService.updateQuestionReport(id, employeeId, replyContent);
        return ResponseEntity.ok().body("{\"message\": \"已處理問題!\"}");
    }



}
