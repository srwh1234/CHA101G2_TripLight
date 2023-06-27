package com.tw.contact.model;

import com.tw.employee.model.Employee;
import com.tw.member.model.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//@NoArgsConstructor
@Data
@Entity
@Table(name="quest_report")
public class QuestionReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "question_content")
    private String questionContent;

    @Column(name = "reply_content")
    private String replyContent;

    @Column(name = "state")
    private int state;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "score")
    private int score;

    public QuestionReport(int id, Member member, Employee employee, String questionContent, String replyContent, int state, LocalDateTime startTime, LocalDateTime endTime, int score) {
        this.id = id;
        this.member = member;
        this.employee = employee;
        this.questionContent = questionContent;
        this.replyContent = replyContent;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }

    public QuestionReport() {

    }
}
