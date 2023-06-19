package com.tw.form.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class CountData {
    int memberNumber;
    int tripNumber;
    int ticketNumber;
    int articleNumber;
    int aiFavoriteNumber;
}
