package com.worksure.worksure.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlipRequest {

    private Double amount;
    private String bankName;
    private String accountNumber;
    private LocalDate paymentDate;
    private String sipImageUrl;
    private String remarks;

    private String workerId;
    private Long userId;
}
