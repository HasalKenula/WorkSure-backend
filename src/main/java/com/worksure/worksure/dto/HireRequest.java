package com.worksure.worksure.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HireRequest {
    public LocalDate bookingDate;

    public LocalTime bookingTime;

    public String description;

    public boolean isBooked;
    public boolean isPending;

    public String workerId;
    public Long userId;
}
