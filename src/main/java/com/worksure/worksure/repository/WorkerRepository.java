package com.worksure.worksure.repository;

import java.util.List;
import java.util.Optional;

import com.worksure.worksure.dto.JobRole;

import com.worksure.worksure.dto.JobRoleCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.worksure.worksure.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, String> {
    @Query("SELECT w FROM Worker w ORDER BY w.id DESC")
    List<Worker> findAllByOrderByIdDesc();

    Optional<Worker> findByUserId(Long userId);

    List<Worker> findByFullNameContainingIgnoreCase(String fullName);

    //List<Worker> findByPreferredServiceLocationContainingIgnoreCase(String preferredServiceLocation);

    //List<Worker> findByJobRole(JobRole jobRole);

    @Query("""
        SELECT w FROM Worker w
        WHERE (:location IS NULL 
               OR LOWER(w.preferredServiceLocation) LIKE LOWER(CONCAT('%', :location, '%')))
          AND (:jobRole IS NULL OR w.jobRole = :jobRole)
          AND w.isBlocked = false
    """)
    List<Worker> searchByLocAndSkill(
            @Param("location") String location,
            @Param("jobRole") JobRole jobRole
    );

    @Query("""
    SELECT new com.worksure.worksure.dto.JobRoleCountDTO(w.jobRole, COUNT(w))
    FROM Worker w
    WHERE w.isBlocked = false
    GROUP BY w.jobRole
    """)
    List<JobRoleCountDTO> countWorkersByJobRole();



}
