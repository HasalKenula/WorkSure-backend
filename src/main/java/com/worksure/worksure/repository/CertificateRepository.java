package com.worksure.worksure.repository;

import com.worksure.worksure.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    boolean existsByWorkerId(String workerId);
}