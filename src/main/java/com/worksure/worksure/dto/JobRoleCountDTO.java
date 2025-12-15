package com.worksure.worksure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JobRoleCountDTO {
    private JobRole jobRole;
    private Long count;
}
