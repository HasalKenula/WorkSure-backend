package com.worksure.worksure.dto;

import lombok.Data;

@Data
public class MonthlyAnalyticsDTO {
    private String month;
    private Long users;
    private Long workers;

    public MonthlyAnalyticsDTO(String month, Long users, Long workers) {
        this.month = month;
        this.users = users;
        this.workers = workers;
    }
}
