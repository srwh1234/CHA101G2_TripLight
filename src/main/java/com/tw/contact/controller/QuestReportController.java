package com.tw.contact.controller;

import com.tw.contact.QuestReportDTO;
import com.tw.contact.modelJPA.QuestReport;
import com.tw.contact.modelJPA.dao.QuestReportRepository;
import com.tw.contact.service.QuestReportService;
import com.tw.member.model.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestReportController {

    private final QuestReportService questReportService;
    private final MemberRepository memberRepository;

    @Autowired
    QuestReportController(QuestReportService questReportService,
                          MemberRepository memberRepository) {
        this.questReportService = questReportService;
        this.memberRepository = memberRepository;
    }


    //送出問題
//    @PostMapping("/createQuestReport")
//    public void save(@RequestBody QuestReportDTO questReportDTO){
//        QuestReport questReport = new QuestReport();
//        questReport.setQContent(questReportDTO.getqContent());
//        questReport.setMember(memberRepository.findByMemberId(2));
//        questReportService.save(questReport);
//    }

//    @GetMapping("/ShowQuestReport")
//    public List<QuestReportDTO> showQuestReport(){
//        List<QuestReport> questReports = questReportService.showQuestReport();
//        List<QuestReportDTO> questReportDTOS = new ArrayList<>();
//        for (QuestReport questReport : questReports) {
//            QuestReportDTO dto = new QuestReportDTO();
//            dto.setId(questReport.getId());
//            dto.setqContent(questReport.getQContent());
//            dto.setrContent(questReport.getRContent());
//            dto.setMemberId(questReport.getMember().getMemberId());
//            questReportDTOS.add(dto);
//        }
//        return questReportDTOS;
//    }


    //依會員邊號顯示問題
//    @GetMapping("/ShowQuestReport")
//    public List<QuestReportDTO> showQuestReport(){
//        List<QuestReport> questReports = questReportService.showQuestReportById(3);
//        List<QuestReportDTO> questReportDTOS = new ArrayList<>();
//        for (QuestReport questReport : questReports) {
//            QuestReportDTO dto = new QuestReportDTO();
//            dto.setId(questReport.getId());
//            dto.setMemberId(questReport.getMember().getMemberId());
//            dto.setqContent(questReport.getQContent());
//            dto.setrContent(questReport.getRContent());
//
//
//            questReportDTOS.add(dto);
//        }
//        return questReportDTOS;
//    }

    //會員查自己的問題回報紀錄
    @GetMapping("/quest_report")
    public List<QuestReport> showMemberQuestionList(@RequestParam("member") int memberId){
        return questReportService.showQuestReportById(memberId);
    }


    @DeleteMapping("/quest_report/{id}")
    public void deleteQuestReport(@PathVariable int id) {
        questReportService.deleteQuestReport();
    }
}
