package com.worksure.worksure.controller;

import com.worksure.worksure.entity.Rating;
import com.worksure.worksure.repository.RatingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@CrossOrigin(origins = "*")
public class RatingController {

    private final RatingRepository ratingRepository;

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    // Create rating
    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        return ratingRepository.save(rating);
    }

    // Get ratings for a worker (WRK001)
    @GetMapping("/{workerId}")
    public List<Rating> getRatingsByWorker(@PathVariable String workerId) {
        return ratingRepository.findByWorker_Id(workerId);
    }

    // Get all ratings
    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
