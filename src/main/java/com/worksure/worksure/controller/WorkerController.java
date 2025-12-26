package com.worksure.worksure.controller;

import java.util.List;

import com.worksure.worksure.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            worker.setPdfUrl(workerRequest.pdfUrl);
            // worker.setBlocked(workerRequest.isBlocked);
            worker.setBlocked(workerRequest.isBlocked);

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

    @GetMapping("/{userId}")
    public ResponseEntity<Worker> getWorkerByUserId(@PathVariable Long userId) {
        Worker worker = workerService.getWorkerByUserId(userId);

        if (worker == null) {
            return ResponseEntity.status(404).body(null);
        } else {
            return ResponseEntity.status(200).body(worker);
        }
    }

    @PutMapping("/toggle-block/{workerId}")
    public ResponseEntity<String> toggleWorkerBlock(@PathVariable String workerId) {
        Worker worker = workerService.getWorkerById(workerId);
        if (worker == null) {
            return ResponseEntity.status(404).body("Worker not found with id: " + workerId);
        }

        worker.setBlocked(!worker.isBlocked()); // toggle
        workerService.updateWorker(worker);

        String status = worker.isBlocked() ? "Blocked" : "Approved";
        return ResponseEntity.ok("Worker " + status + " successfully");
    }

    @GetMapping("/id/{workerId}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable String workerId) {
        Worker worker = workerService.getWorkerById(workerId);
        if (worker == null) {
            return ResponseEntity.status(404).body(null);
        } else {
            return ResponseEntity.ok(worker);
        }
    }

    @GetMapping("/searchbyname")
    public List<Worker> searchByName(@RequestParam String keyword) {
        return workerService.search(keyword);
    }

    @GetMapping("/searchbylocandskill")
    public List<Worker> searchByLocAndSkill(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) JobRole jobRole) {
        return workerService.searchByLocAndSkill(location, jobRole);
    }

    @GetMapping("/job-roles")
    public List<JobRoleCountDTO> getJobRolesWithCount() {
        return workerService.getWorkerCountByJobRole();
    }

    @PutMapping("/{workerId}")
    public ResponseEntity<Worker> updateWorker(
            @RequestBody WorkerRequest workerRequest,
            @PathVariable String workerId) {

        try {
            Worker existingWorker = workerService.getWorkerById(workerId);

            if (existingWorker == null) {
                return ResponseEntity.status(404).body(null);
            }

            existingWorker.setFullName(workerRequest.fullName);
            existingWorker.setEmail(workerRequest.email);
            existingWorker.setPhoneNumber(workerRequest.phoneNumber);
            existingWorker.setNic(workerRequest.nic);
            existingWorker.setAddress(workerRequest.address);
            existingWorker.setJobRole(workerRequest.jobRole);

            existingWorker.setMon(workerRequest.mon);
            existingWorker.setTue(workerRequest.tue);
            existingWorker.setWed(workerRequest.wed);
            existingWorker.setThu(workerRequest.thu);
            existingWorker.setFri(workerRequest.fri);
            existingWorker.setSat(workerRequest.sat);
            existingWorker.setSun(workerRequest.sun);

            existingWorker.setPreferredStartTime(workerRequest.preferredStartTime);
            existingWorker.setPreferredEndTime(workerRequest.preferredEndTime);
            existingWorker.setPreferredServiceLocation(workerRequest.preferredServiceLocation);
            existingWorker.setPdfUrl(workerRequest.pdfUrl);

            existingWorker.getCertificates().clear();
            if (workerRequest.certificates != null) {
                for (CertificateRequest cReq : workerRequest.certificates) {
                    Certificate certificate = new Certificate();
                    certificate.setCertificateName(cReq.certificateName);
                    certificate.setIssuingBody(cReq.issuingBody);
                    certificate.setWorker(existingWorker);
                    existingWorker.getCertificates().add(certificate);
                }
            }

            existingWorker.getJobExperiences().clear();
            if (workerRequest.jobExperiences != null) {
                for (JobExperienceRequest jReq : workerRequest.jobExperiences) {
                    JobExperience jobExp = new JobExperience();
                    jobExp.setCompanyName(jReq.companyName);
                    jobExp.setJobTitle(jReq.jobTitle);
                    jobExp.setYears(jReq.years);
                    jobExp.setWorker(existingWorker);
                    existingWorker.getJobExperiences().add(jobExp);
                }
            }

            Worker updatedWorker = workerService.updateWorkerById(workerId, existingWorker);
            return ResponseEntity.ok(updatedWorker);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

}
