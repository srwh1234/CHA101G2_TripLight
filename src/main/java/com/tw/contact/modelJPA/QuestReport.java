package com.tw.contact.modelJPA;

import com.tw.employee.model.Employee;
import com.tw.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

//@NoArgsConstructor
@Data //@Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@Entity
@Table(name="quest_report")
public class QuestReport {

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

    @Column(name = "q_content")
    private String qContent="";

    @Column(name = "r_content")
    private String rContent;

    @Column(name = "state")
    private int state;

    @Column(name = "start_time")
    private LocalDateTime startTime = LocalDateTime.now();

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "score")
    private int score;

    public QuestReport() {
    }

    public QuestReport(Member member, Employee employee, String qContent, String rContent, int state, LocalDateTime startTime, LocalDateTime endTime, int score) {
        this.member = member;
        this.employee = employee;
        this.qContent = qContent;
        this.rContent = rContent;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }
}
