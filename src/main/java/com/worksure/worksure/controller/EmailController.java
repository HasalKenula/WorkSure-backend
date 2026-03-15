package com.worksure.worksure.controller;

import com.worksure.worksure.dto.EmailRequest;
import com.worksure.worksure.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/email")
@CrossOrigin("*")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request){
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email){
        String token = UUID.randomUUID().toString();
        String resetLink = "http://localhost:8081/reset-password?token=" + token;
        emailService.sendResetEmail(email,resetLink);
        return "Reset password link sent to your email";
    }
}
