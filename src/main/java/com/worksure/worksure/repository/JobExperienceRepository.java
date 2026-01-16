package com.worksure.worksure.repository;

import com.worksure.worksure.entity.JobExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobExperienceRepository extends JpaRepository<JobExperience, Long> {

    List<JobExperience> findByWorkerId(String workerId);
}

