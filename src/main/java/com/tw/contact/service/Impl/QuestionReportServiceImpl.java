package com.tw.contact.service.Impl;

import com.tw.contact.controller.dto.QuestionReportRequestDTO;
import com.tw.contact.model.QuestionReport;
import com.tw.contact.model.dao.QuestionReportRepository;
import com.tw.contact.service.QuestionReportService;
import com.tw.employee.dao.EmployeeRepository;
import com.tw.member.model.dao.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionReportServiceImpl implements QuestionReportService {
    private final QuestionReportRepository questionReportRepository;
    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;


    @Autowired
    public QuestionReportServiceImpl(QuestionReportRepository questionReportRepository, MemberRepository memberRepository, EmployeeRepository employeeRepository) {
        this.questionReportRepository = questionReportRepository;
        this.memberRepository = memberRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createQuestionReport(QuestionReportRequestDTO questionReportRequestDTO) {
        QuestionReport questionReport = new QuestionReport();
        questionReport.setMember(memberRepository.findByMemberId(questionReportRequestDTO.getMemberId()));
        questionReport.setQuestionContent(questionReportRequestDTO.getQuestionContent());
        questionReport.setStartTime(LocalDateTime.now());
        save(questionReport);
    }

    @Override
    public void save(QuestionReport questReport) {
        questionReportRepository.save(questReport);
    }

    @Override
    public List<QuestionReport> showQuestionReportById(int memberId) {
        return questionReportRepository.findByMemberMemberId(memberId);
    }

    @Override
    public void deleteQuestionReport(int id) {
        questionReportRepository.deleteById(id);
    }

    @Override
    public void updateScore(int id, int score) {
        QuestionReport questionReport = questionReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question report Id:" + id));
        questionReport.setScore(score);
        questionReportRepository.save(questionReport);
    }

    @Override
    public QuestionReport checkQuestionDetail(int id) {
        return questionReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question report Id:" + id));
    }

    /*
    後臺服務
     */
    @Override
    public List<QuestionReport> showQuestionReportByState(int state) {
       return questionReportRepository.findByState(0);
    }

    @Override
    public void updateQuestionReport(int id, int employeeId, String replyContent) {
        QuestionReport questionReport;
        questionReport = questionReportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid question report Id:" + id));
        questionReport.setEmployee(employeeRepository.findByEmployeeId(employeeId));
        questionReport.setReplyContent(replyContent);
        questionReport.setState(1);
        questionReport.setEndTime(LocalDateTime.now());
        save(questionReport);
    }


}