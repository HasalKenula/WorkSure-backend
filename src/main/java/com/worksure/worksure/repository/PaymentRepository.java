
package com.worksure.worksure.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worksure.worksure.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

   Optional<Payment> findByUserId(Long userId);
}
