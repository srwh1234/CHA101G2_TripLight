package com.tw.contact.modelJPA;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name="chat_record")
public class ChatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="employee_id")
    private int employeeId;

    @Column(name="member_id")
    private int memberId;

    @Column(name="postTime")
    private LocalDateTime postTime;

    @Column(name="content")
    private String content;

    @Column(name="status")
    private int Status;

    public ChatRecord(int employeeId, int memberId, LocalDateTime postTime, String content, int status) {
        this.employeeId = employeeId;
        this.memberId = memberId;
        this.postTime = postTime;
        this.content = content;
        Status = status;
    }
}
