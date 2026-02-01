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

import com.worksure.worksure.dto.TransferRequest;

import com.worksure.worksure.entity.Transfer;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.entity.Worker;
import com.worksure.worksure.service.TransferService;
import com.worksure.worksure.service.UserService;
import com.worksure.worksure.service.WorkerService;

@RestController
@CrossOrigin(origins = "*")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    @GetMapping("/transfer")
    public ResponseEntity<List<Transfer>> getAllTransfer() {
        List<Transfer> allTransfers = transferService.getAllTransfer();
        return ResponseEntity.status(200).body(allTransfers);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> createTransfer(@RequestBody TransferRequest transferRequest) {
        try {
            Transfer newTransfer = new Transfer();
            newTransfer.setTransactionId(transferRequest.getTransactionId());
            newTransfer.setFullName(transferRequest.getFullName());
            newTransfer.setAmount(transferRequest.getAmount());
            newTransfer.setPaymentMethod(transferRequest.getPaymentMethod());
            User user = userService.getUserById(transferRequest.getUserId());
            newTransfer.setUser(user);
            Worker worker = workerService.getWorkerById(transferRequest.getWorkerId());
            newTransfer.setWorker(worker);
            Transfer saveTransfer = transferService.createTransfer(newTransfer);
            return ResponseEntity.status(200).body(saveTransfer);

        } catch (Exception e) {
            return ResponseEntity.status(200).body("error" + e.getMessage());
        }
    }

}
