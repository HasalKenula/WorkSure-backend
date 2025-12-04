package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Worker;

@Service
public interface WorkerService {
    Worker createWorker(Worker worker);

    List<Worker> getAllWorkers();

    Worker getWorkerByUserId(Long userId);
}
