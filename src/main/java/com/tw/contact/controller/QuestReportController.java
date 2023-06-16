package com.tw.contact.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tw.contact.modelJPA.QuestReport;
import com.tw.contact.service.QuestReportService;
import com.tw.member.model.dao.MemberRepository;

@RestController
public class QuestReportController {

	private final QuestReportService questReportService;
	private final MemberRepository memberRepository;

	@Autowired
	QuestReportController(final QuestReportService questReportService, final MemberRepository memberRepository) {
		this.questReportService = questReportService;
		this.memberRepository = memberRepository;
	}

	// 送出問題
	// @PostMapping("/createQuestReport")
	// public void save(@RequestBody QuestReportDTO questReportDTO){
	// QuestReport questReport = new QuestReport();
	// questReport.setQContent(questReportDTO.getqContent());
	// questReport.setMember(memberRepository.findByMemberId(2));
	// questReportService.save(questReport);
	// }

	// @GetMapping("/ShowQuestReport")
	// public List<QuestReportDTO> showQuestReport(){
	// List<QuestReport> questReports = questReportService.showQuestReport();
	// List<QuestReportDTO> questReportDTOS = new ArrayList<>();
	// for (QuestReport questReport : questReports) {
	// QuestReportDTO dto = new QuestReportDTO();
	// dto.setId(questReport.getId());
	// dto.setqContent(questReport.getQContent());
	// dto.setrContent(questReport.getRContent());
	// dto.setMemberId(questReport.getMember().getMemberId());
	// questReportDTOS.add(dto);
	// }
	// return questReportDTOS;
	// }

	// 依會員邊號顯示問題
	// @GetMapping("/ShowQuestReport")
	// public List<QuestReportDTO> showQuestReport(){
	// List<QuestReport> questReports = questReportService.showQuestReportById(3);
	// List<QuestReportDTO> questReportDTOS = new ArrayList<>();
	// for (QuestReport questReport : questReports) {
	// QuestReportDTO dto = new QuestReportDTO();
	// dto.setId(questReport.getId());
	// dto.setMemberId(questReport.getMember().getMemberId());
	// dto.setqContent(questReport.getQContent());
	// dto.setrContent(questReport.getRContent());
	//
	//
	// questReportDTOS.add(dto);
	// }
	// return questReportDTOS;
	// }

	// 會員查自己的問題回報紀錄
	@GetMapping("/quest_report")
	public List<QuestReport> showMemberQuestionList(@RequestParam("member") final int memberId) {
		return questReportService.showQuestReportById(memberId);
	}

	@DeleteMapping("/quest_report/{id}")
	public void deleteQuestReport(@PathVariable final int id) {
		// questReportService.deleteQuestReport();
	}
}
