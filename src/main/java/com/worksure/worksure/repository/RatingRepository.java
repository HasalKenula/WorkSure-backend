package com.worksure.worksure.repository;

import com.worksure.worksure.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByWorker_Id(String workerId);
}
