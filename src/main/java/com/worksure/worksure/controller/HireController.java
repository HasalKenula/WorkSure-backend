package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksure.worksure.dto.HireRequest;
import com.worksure.worksure.entity.Hire;
import com.worksure.worksure.service.HireService;
import com.worksure.worksure.service.WorkerService;

@RestController
@CrossOrigin(origins = "*")
public class HireController {
    @Autowired
    private HireService hireService;

    @Autowired
    private WorkerService workerService;

    @PostMapping("/hire")
    public ResponseEntity<Hire> createHire(@RequestBody HireRequest hireRequest) {
        Hire hire=new Hire();
        hire.setBookingDate(hireRequest.getBookingDate());
        hire.setBookingTime(hireRequest.getBookingTime());
        hire.setDescription(hireRequest.getDescription());
        hire.setWorker(workerService.getWorkerById(hireRequest.getWorkerId()));
        Hire hireData=hireService.createHire(hire);
        return ResponseEntity.status(200).body(hireData);
    }

    @GetMapping("/hire")
    public ResponseEntity<List<Hire>> getAllHire() {
        List<Hire> hires = hireService.getAllHire();
        return ResponseEntity.status(200).body(hires);
    }

}
