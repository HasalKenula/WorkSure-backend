package com.worksure.worksure.service;

import java.util.List;

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
        return workerRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(keywords, keywords);
    }


}
