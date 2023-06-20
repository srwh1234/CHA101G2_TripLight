package com.tw.contact;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class QuestionReportRequestDTO {
    private int memberId;
    private String questionContent;
}
