package com.worksure.worksure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worksure.worksure.entity.BankDetails;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long>{
    Optional<List<BankDetails>> findByUserId(Long userId);
}
