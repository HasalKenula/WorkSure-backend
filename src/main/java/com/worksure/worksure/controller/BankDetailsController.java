package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksure.worksure.dto.BankDetailsRequest;
import com.worksure.worksure.entity.BankDetails;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.entity.Worker;
import com.worksure.worksure.service.BankDetailsService;
import com.worksure.worksure.service.UserService;
import com.worksure.worksure.service.WorkerService;

@RestController
@CrossOrigin(origins = "*")
public class BankDetailsController {
    @Autowired
    private BankDetailsService bankDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    @PostMapping("/bank")
    public ResponseEntity<?> createBankDetails(@RequestBody BankDetailsRequest bankDetailsRequest) {
        try {
            BankDetails newBankDetails = new BankDetails();
            newBankDetails.setFullName(bankDetailsRequest.getFullName());
            newBankDetails.setEmail(bankDetailsRequest.getEmail());
            newBankDetails.setContact(bankDetailsRequest.getContact());
            newBankDetails.setNic(bankDetailsRequest.getNic());
            newBankDetails.setBankName(bankDetailsRequest.getBankName());
            newBankDetails.setAccountNumber(bankDetailsRequest.getAccountNumber());
            newBankDetails.setHolder(bankDetailsRequest.getHolder());
            newBankDetails.setAccountType(bankDetailsRequest.getAccountType());
            newBankDetails.setBranch(bankDetailsRequest.getBranch());
            newBankDetails.setBranchCode(bankDetailsRequest.getBranchCode());
            newBankDetails.setAddress(bankDetailsRequest.getAddress());
            User user = userService.getUserById(bankDetailsRequest.getUserId());
            newBankDetails.setUser(user);
            Worker worker = workerService.getWorkerById(bankDetailsRequest.getWorkerId());
            newBankDetails.setWorker(worker);
            BankDetails saveBankDetails = bankDetailsService.createBankDetails(newBankDetails);
            return ResponseEntity.status(200).body(saveBankDetails);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/bank")
    public ResponseEntity<List<BankDetails>> getBankDetails() {
        List<BankDetails> bankDetails = bankDetailsService.getBankDetails();
        return ResponseEntity.status(200).body(bankDetails);
    }

    @GetMapping("/bank/{userId}")
    public ResponseEntity<List<BankDetails>> getBankByUserId(@PathVariable Long userId) {

        List<BankDetails> banks = bankDetailsService.getBankByUserId(userId);

        if (banks == null || banks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(200).body(banks);
    }

}
