package com.worksure.worksure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
    private Long userId;
    private String workerId;
    private int rating;
    private String feedback;
}
