package com.worksure.worksure.service;

import java.util.List;

import com.worksure.worksure.dto.JobRole;

import com.worksure.worksure.dto.JobRoleCountDTO;
import com.worksure.worksure.dto.WorkerRatingDTO;
import com.worksure.worksure.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksure.worksure.dto.WorkerIdGenerator;

import com.worksure.worksure.entity.Certificate;
import com.worksure.worksure.entity.JobExperience;
import com.worksure.worksure.entity.Worker;
import com.worksure.worksure.repository.WorkerRepository;

@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerIdGenerator workerIdGenerator;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Worker createWorker(Worker worker) {
        if (worker.getId() == null || worker.getId().isEmpty()) {
            worker.setId(workerIdGenerator.generateWorkerId());
        }

        if (worker.getCertificates() != null) {
            for (Certificate c : worker.getCertificates()) {
                c.setWorker(worker);
            }
        }

        if (worker.getJobExperiences() != null) {
            for (JobExperience j : worker.getJobExperiences()) {
                j.setWorker(worker);
            }
        }

        return workerRepository.save(worker);
    }

    private double getAvgRating(Worker worker) {

        if(worker.getRatings() == null || worker.getRatings().isEmpty()){
            return 0;
        }

        return worker.getRatings()
                .stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0);
    }

    @Override
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Worker getWorkerByUserId(Long userId) {
        return workerRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public Worker getWorkerById(String workerId) {
        return workerRepository.findById(workerId).orElse(null);
    }

    @Override
    public Worker updateWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public List<Worker> search(String keywords) {
        return workerRepository.findByFullNameContainingIgnoreCase(keywords);
    }

    @Override
    public List<Worker> searchByLocAndSkill(String location, JobRole jobRole, String sort) {

//        if (location != null && location.trim().isEmpty()) {
//            location = null;
//        }
//
//        return workerRepository.searchByLocAndSkill(location, jobRole);

        List<Worker> workers = workerRepository.searchByLocAndSkill(location, jobRole);

        if ("high".equalsIgnoreCase(sort)) {
            workers.sort((a, b) -> Double.compare(getAvgRating(b), getAvgRating(a)));
        }

        if ("low".equalsIgnoreCase(sort)) {
            workers.sort((a, b) -> Double.compare(getAvgRating(a), getAvgRating(b)));
        }

        return workers;
    }

    @Override
    public List<JobRoleCountDTO> getWorkerCountByJobRole() {
        return workerRepository.countWorkersByJobRole();
    }

    @Override
    public Worker updateWorkerById(String workerId, Worker worker) {

        if (!workerRepository.existsById(workerId)) {
            return null;
        }

        return workerRepository.save(worker);
    }

    @Override
    public List<WorkerRatingDTO> getWorkersSortedByRating(String order) {

        if(order.equals("high")){
            return ratingRepository.findWorkersOrderByRatingDesc();
        }
        else{
            return ratingRepository.findWorkersOrderByRatingAsc();
        }
    }

}
