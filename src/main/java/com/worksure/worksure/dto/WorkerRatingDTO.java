package com.worksure.worksure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkerRatingDTO {

    private String workerId;
    private String fullName;
    private JobRole jobRole;
    private String location;
    private Double rating;

}