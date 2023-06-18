package com.tw.contact;

import com.tw.member.model.Member;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

public class QuestReportRequestDTO {
    private String qContent;
    private int memberId;

    private LocalDateTime startTime = LocalDateTime.now();

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "QuestReportRequestDTO{" +
                "qContent='" + qContent + '\'' +
                ", memberId=" + memberId +
                ", startTime=" + startTime +
                '}';
    }
}
