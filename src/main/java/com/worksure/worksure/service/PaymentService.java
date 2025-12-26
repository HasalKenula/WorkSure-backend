package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.worksure.worksure.entity.Payment;


@Service
public interface PaymentService {
    Payment createPayment(Payment payment);

    List<Payment> getAllPayments();
}
