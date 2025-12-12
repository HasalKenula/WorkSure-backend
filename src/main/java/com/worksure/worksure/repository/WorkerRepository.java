package com.worksure.worksure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.worksure.worksure.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, String> {
    @Query("SELECT w FROM Worker w ORDER BY w.id DESC")
    List<Worker> findAllByOrderByIdDesc();

    Optional<Worker> findByUserId(Long userId);

    List<Worker> findByFullNameContainingIgnoreCase(String fullName);

    List<Worker> findByPreferredServiceLocationContainingIgnoreCase(String preferredServiceLocation);
}
