package com.worksure.worksure.service;


import com.worksure.worksure.dto.MonthlyAnalyticsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnalyticsService {

    public List<MonthlyAnalyticsDTO> getMonthlyAnalytics();

}
