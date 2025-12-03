package com.worksure.worksure.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksure.worksure.entity.Worker;
import com.worksure.worksure.repository.WorkerRepository;

@Component
public class WorkerIdGenerator {
    @Autowired
    private WorkerRepository workerRepository;

    public String generateWorkerId() {
        List<Worker> workers = workerRepository.findAllByOrderByIdDesc();

        if (workers.isEmpty()) {
            return "W0001";
        }

        String lastId = workers.get(0).getId();
        int numericPart = Integer.parseInt(lastId.substring(1));
        numericPart++;
        return String.format("W%04d", numericPart);
    }
}
