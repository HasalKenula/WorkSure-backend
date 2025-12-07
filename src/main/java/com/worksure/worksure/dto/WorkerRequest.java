package com.worksure.worksure.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerRequest {
    public String fullName;
    public String email;
    public String phoneNumber;
    public String nic;
    public String address;

    public boolean mon;
    public boolean tue;
    public boolean wed;
    public boolean thu;
    public boolean fri;
    public boolean sat;
    public boolean sun;

    public LocalTime preferredStartTime;
    public LocalTime preferredEndTime;
    public String preferredServiceLocation;

    public boolean isBlocked;

    public JobRole jobRole;
    public Long userId;

    public List<CertificateRequest> certificates;
    public List<JobExperienceRequest> jobExperiences;
}
