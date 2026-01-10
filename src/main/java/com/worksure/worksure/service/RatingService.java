package com.worksure.worksure.service;

import com.worksure.worksure.dto.RatingRequest;
import com.worksure.worksure.entity.Rating;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.entity.Worker;
import com.worksure.worksure.repository.RatingRepository;
import com.worksure.worksure.repository.UserRepository;
import com.worksure.worksure.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private WorkerRepository workerRepo;

    public Rating createRating(RatingRequest req) {
        User user = userRepo.findById(req.getUserId()).orElseThrow();
        Worker worker = workerRepo.findById(req.getWorkerId()).orElseThrow();

        Rating r = new Rating();
        r.setUser(user);
        r.setWorker(worker);
        r.setRating(req.getRating());
        r.setFeedback(req.getFeedback());

        return ratingRepo.save(r);
    }

    public List<Rating> getRatingsForWorker(String workerId) {
        return ratingRepo.findByWorkerId(workerId);
    }

    // average rating
    public double getAverageRating(String workerId) {
        List<Rating> ratings = ratingRepo.findByWorkerId(workerId);
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }
}
