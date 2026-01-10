package com.worksure.worksure.controller;

import com.worksure.worksure.dto.RatingRequest;
import com.worksure.worksure.entity.Rating;
import com.worksure.worksure.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rating")
@CrossOrigin(origins = "http://localhost:5173")
public class RatingController {

    @Autowired
    private RatingService service;

    @PostMapping
    public Rating addRating(@RequestBody RatingRequest req) {
        return service.createRating(req);
    }

    @GetMapping("/{workerId}")
    public Map<String, Object> getRatingsForWorker(@PathVariable String workerId) {
        Map<String, Object> map = new HashMap<>();
        map.put("ratings", service.getRatingsForWorker(workerId));
        map.put("average", service.getAverageRating(workerId));
        return map;
    }
}
