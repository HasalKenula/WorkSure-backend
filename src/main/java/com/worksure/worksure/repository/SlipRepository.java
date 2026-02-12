package com.worksure.worksure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worksure.worksure.entity.Slip;

public interface SlipRepository extends JpaRepository<Slip, Long> {
    
}
