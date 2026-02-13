package com.worksure.worksure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worksure.worksure.entity.Slip;

public interface SlipRepository extends JpaRepository<Slip, Long> {
    List<Slip> findByWorkerId(String workerId);
}
