package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksure.worksure.dto.HireRequest;
import com.worksure.worksure.entity.Hire;
import com.worksure.worksure.service.HireService;
import com.worksure.worksure.service.UserService;
import com.worksure.worksure.service.WorkerService;

@RestController
@CrossOrigin(origins = "*")
public class HireController {
    @Autowired
    private HireService hireService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private UserService userService;

    @PostMapping("/hire")
    public ResponseEntity<Hire> createHire(@RequestBody HireRequest hireRequest) {
        Hire hire = new Hire();
        hire.setBookingDate(hireRequest.bookingDate);
        hire.setBookingTime(hireRequest.bookingTime);
        hire.setDescription(hireRequest.description);
        hire.setBooked(hireRequest.isBooked);
        hire.setWorker(workerService.getWorkerById(hireRequest.getWorkerId()));
        hire.setUser(userService.getUserById(hireRequest.getUserId()));
        Hire hireData = hireService.createHire(hire);
        return ResponseEntity.status(200).body(hireData);
    }

    @GetMapping("/hire")
    public ResponseEntity<List<Hire>> getAllHire() {
        List<Hire> hires = hireService.getAllHire();
        return ResponseEntity.status(200).body(hires);
    }

    @GetMapping("/hire/{workerId}")
    public ResponseEntity<?> getHireByWorkerId(@PathVariable String workerId) {
        List<Hire> hires = hireService.getHireByWorkerId(workerId);

        if (hires.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("No hires found for worker ID: " + workerId);
        }

        return ResponseEntity.ok(hires);
    }

    @PutMapping("/hire/toggle-block/{hireId}")
    public ResponseEntity<String> toggleHireBlock(@PathVariable Long hireId) {
        Hire hire = hireService.getHireById(hireId);

        if (hire == null) {
            return ResponseEntity.status(404).body("Hire not found with id: " + hireId);
        }

        hire.setBooked(!hire.isBooked()); // toggle value
        hireService.updateHire(hire);

        String status = hire.isBooked() ? "Blocked" : "Approved";
        return ResponseEntity.ok("Hire " + status + " successfully");
    }

}
