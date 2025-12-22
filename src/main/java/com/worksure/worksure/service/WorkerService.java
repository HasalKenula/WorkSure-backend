package com.worksure.worksure.service;

import java.util.List;

import com.worksure.worksure.dto.JobRole;
import com.worksure.worksure.dto.JobRoleCountDTO;
import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Worker;

@Service
public interface WorkerService {
    Worker createWorker(Worker worker);

    List<Worker> getAllWorkers();

    Worker getWorkerByUserId(Long userId);

    Worker getWorkerById(String workerId);

    Worker updateWorker(Worker worker);

    List<Worker> search(String keywords);

    List<Worker> searchByLocAndSkill(String location, JobRole skill);

    List<JobRoleCountDTO> getWorkerCountByJobRole();

    Worker updateWorkerById(String workerId, Worker worker);

}
