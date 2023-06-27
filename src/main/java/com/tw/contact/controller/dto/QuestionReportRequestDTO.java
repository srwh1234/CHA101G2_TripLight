package com.tw.contact.controller.dto;

import lombok.Data;

@Data
public class QuestionReportRequestDTO {
    private int memberId;
    private String questionContent;
}
