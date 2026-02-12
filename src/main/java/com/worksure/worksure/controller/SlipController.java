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

import com.worksure.worksure.dto.SlipRequest;
import com.worksure.worksure.entity.Slip;
import com.worksure.worksure.service.SlipService;
import com.worksure.worksure.service.UserService;
import com.worksure.worksure.service.WorkerService;

@RestController
@CrossOrigin(origins = "*")
public class SlipController {

    @Autowired
    private SlipService slipService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private UserService userService;

    @PostMapping("/slip")
    public ResponseEntity<Slip> createSlip(@RequestBody SlipRequest slipRequest) {

        Slip slip = new Slip();

        slip.setAmount(slipRequest.getAmount());
        slip.setBankName(slipRequest.getBankName());
        slip.setAccountNumber(slipRequest.getAccountNumber());
        slip.setPaymentDate(slipRequest.getPaymentDate());
        slip.setSipImageUrl(slipRequest.getSipImageUrl());
        slip.setRemarks(slipRequest.getRemarks());

        slip.setWorker(
                workerService.getWorkerById(slipRequest.getWorkerId()));

        slip.setUser(
                userService.getUserById(slipRequest.getUserId()));

        Slip savedSlip = slipService.createSlip(slip);

        return ResponseEntity.ok(savedSlip);
    }

    @GetMapping("/slip/{workerId}")
    public ResponseEntity<List<Slip>> getSlipByWorkerId(@PathVariable String workerId) {
        List<Slip> allSlips = slipService.getSlipByWorkerId(workerId);
        return ResponseEntity.status(200).body(allSlips);
    }

}
