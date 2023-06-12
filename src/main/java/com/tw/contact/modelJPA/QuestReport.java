package com.tw.contact.modelJPA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data //@Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@Entity
@Table(name="quest_report")
public class QuestReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "q_content")
    private String qContent;

    @Column(name = "r_content")
    private String rContent;

    @Column(name = "state")
    private int state;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "score")
    private int score;


    public QuestReport(int memberId, int employeeId, String qContent, String rContent, int state, LocalDateTime startTime, LocalDateTime endTime, int score) {
        this.memberId = memberId;
        this.employeeId = employeeId;
        this.qContent = qContent;
        this.rContent = rContent;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }
}
