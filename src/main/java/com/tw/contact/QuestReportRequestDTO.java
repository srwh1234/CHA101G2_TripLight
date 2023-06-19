package com.tw.contact;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestReportRequestDTO {
    private int memberId;
    private String qContent;
    private LocalDateTime startTime = LocalDateTime.now();

}
