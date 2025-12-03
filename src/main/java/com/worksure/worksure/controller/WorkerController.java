package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worksure.worksure.dto.CertificateRequest;
import com.worksure.worksure.dto.JobExperienceRequest;
import com.worksure.worksure.dto.WorkerRequest;
import com.worksure.worksure.entity.Certificate;
import com.worksure.worksure.entity.JobExperience;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.entity.Worker;
import com.worksure.worksure.service.UserService;
import com.worksure.worksure.service.WorkerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @Autowired
    private UserService userService; 

    @GetMapping
    public ResponseEntity<List<Worker>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @PostMapping
    public ResponseEntity<String> createWorker(@RequestBody WorkerRequest workerRequest) {
        try {
            Worker worker = new Worker();

            worker.setFullName(workerRequest.fullName);
            worker.setEmail(workerRequest.email);
            worker.setPhoneNumber(workerRequest.phoneNumber);
            worker.setNic(workerRequest.nic);
            worker.setAddress(workerRequest.address);
            worker.setJobRole(workerRequest.jobRole);

           
            worker.setMon(workerRequest.mon);
            worker.setTue(workerRequest.tue);
            worker.setWed(workerRequest.wed);
            worker.setThu(workerRequest.thu);
            worker.setFri(workerRequest.fri);
            worker.setSat(workerRequest.sat);
            worker.setSun(workerRequest.sun);

            worker.setPreferredStartTime(workerRequest.preferredStartTime);
            worker.setPreferredEndTime(workerRequest.preferredEndTime);
            worker.setPreferredServiceLocation(workerRequest.preferredServiceLocation);

          
            if (workerRequest.userId != null) {
                User user = userService.getUserById(workerRequest.userId);
                if (user == null) {
                    return ResponseEntity.status(400).body("User not found with id: " + workerRequest.userId);
                }
                worker.setUser(user);
            }

            // Certificates
            if (workerRequest.certificates != null) {
                for (CertificateRequest cReq : workerRequest.certificates) {
                    Certificate c = new Certificate();
                    c.setCertificateName(cReq.certificateName);
                    c.setIssuingBody(cReq.issuingBody);
                    c.setWorker(worker);
                    worker.getCertificates().add(c);
                }
            }

            // Job Experiences
            if (workerRequest.jobExperiences != null) {
                for (JobExperienceRequest jReq : workerRequest.jobExperiences) {
                    JobExperience j = new JobExperience();
                    j.setCompanyName(jReq.companyName);
                    j.setJobTitle(jReq.jobTitle);
                    j.setYears(jReq.years);
                    j.setWorker(worker);
                    worker.getJobExperiences().add(j);
                }
            }

            Worker savedWorker = workerService.createWorker(worker);
            return ResponseEntity.ok("Worker Created Successfully with ID: " + savedWorker.getId());
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(500).body("Error creating worker: " + e.getMessage());
        }
    }

}
