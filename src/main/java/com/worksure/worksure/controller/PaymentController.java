package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.worksure.worksure.dto.PaymentRequest;
import com.worksure.worksure.entity.Payment;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.service.PaymentService;
import com.worksure.worksure.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {

        try {
            Payment payment = new Payment();
            payment.setName(paymentRequest.getName());
            payment.setAddress(paymentRequest.getAddress());
            payment.setEmail(paymentRequest.getEmail());
            payment.setAmount(paymentRequest.getAmount());
            payment.setPlanName(paymentRequest.getPlanName());

            User user = userService.getUserById(paymentRequest.getUserId());
            payment.setUser(user);
            Payment savePayment = paymentService.createPayment(payment);
            return ResponseEntity.status(200).body(savePayment);
           
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
