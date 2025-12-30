// package com.worksure.worksure.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;

// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;


// import com.worksure.worksure.entity.Payment;

// import com.worksure.worksure.service.PaymentService;


// @RestController
// @CrossOrigin(origins = "*")
// public class PaymentController {

//    @Autowired
//     public PaymentService paymentService;

   

//     @GetMapping("/payment")
//     public ResponseEntity<List<Payment>> getAllPayments() {
//         List<Payment> payment = paymentService.getAllPayments();

//         return ResponseEntity.status(200).body(payment);

//     }

//     @PostMapping("/payment")
//     public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
//        return paymentService.createPayment(payment);
//     }
// }


package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.worksure.worksure.entity.Payment;
import com.worksure.worksure.service.PaymentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(savedPayment);
    }
}
