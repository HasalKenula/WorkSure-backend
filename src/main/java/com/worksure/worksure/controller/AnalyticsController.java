package com.worksure.worksure.controller;

import com.worksure.worksure.dto.MonthlyAnalyticsDTO;
import com.worksure.worksure.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/monthly-registrations")
    public List<MonthlyAnalyticsDTO> getMonthlyRegistrations() {
        return analyticsService.getMonthlyAnalytics();
    }
}
