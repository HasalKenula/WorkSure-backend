package com.worksure.worksure.controller;


import com.worksure.worksure.entity.Rating;
import com.worksure.worksure.repository.RatingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@CrossOrigin(origins = "http://localhost:5173")
public class RatingController {

    private final RatingRepository ratingRepository;

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        return ratingRepository.save(rating);
    }

    @GetMapping("/{itemId}")
    public List<Rating> getRatings(@PathVariable("itemId") Long itemId) {
        return ratingRepository.findByItemId(itemId);
    }

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
