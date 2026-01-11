package com.worksure.worksure.service;

import com.worksure.worksure.dto.MonthlyAnalyticsDTO;
import com.worksure.worksure.repository.UserRepository;
import com.worksure.worksure.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WorkerRepository workerRepo;

    public List<MonthlyAnalyticsDTO> getMonthlyAnalytics(){
        Map<Integer, Long> userMap = new HashMap<>();
        for (Object[] row : userRepo.countUsersByMonth()) {
            userMap.put((Integer) row[0], (Long) row[1]);
        }

        Map<Integer, Long> workerMap = new HashMap<>();
        for (Object[] row : workerRepo.countWorkersByMonth()) {
            workerMap.put((Integer) row[0], (Long) row[1]);
        }

        List<MonthlyAnalyticsDTO> result = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            result.add(new MonthlyAnalyticsDTO(
                    Month.of(i).name().substring(0, 3),
                    userMap.getOrDefault(i, 0L),
                    workerMap.getOrDefault(i, 0L)
            ));
        }
        return result;
    }
}
