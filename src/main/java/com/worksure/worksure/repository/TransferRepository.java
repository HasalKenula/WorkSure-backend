package com.worksure.worksure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worksure.worksure.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>{
    List<Transfer> findByWorkerId(String workerId);
}
