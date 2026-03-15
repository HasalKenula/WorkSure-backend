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
    @Autowired
    private final EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        User user = userRepository.findByEmail(email);

        if(user == null){
            return "Email not found";
        }

        String token = UUID.randomUUID().toString();

        // Delete any existing token for this user
        tokenRepository.deleteByUserId(user.getId());

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:5173/reset-password?token=" + token;
        emailService.sendResetEmail(user.getEmail(),resetLink);
        return "Reset password link sent to your email";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request){
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken());

        if(resetToken == null){
            return "Invalid token";
        }

        if(resetToken.getExpiryDate().isBefore(LocalDateTime.now())){
            return "Token expired";
        }

        User user = resetToken.getUser();

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);


        return "Password updated successfully";
    }
}
