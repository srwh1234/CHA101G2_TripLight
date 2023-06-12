package com.tw.contact.modelJPA;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data //@Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@Entity
@Table(name="faq")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="faq_type_id")
    private FaqType faqType;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    public Faq(FaqType faqType, LocalDateTime time, int employeeId, String question, String answer) {
        this.faqType = faqType;
        this.time = time;
        this.employeeId = employeeId;
        this.question = question;
        this.answer = answer;
    }
}
