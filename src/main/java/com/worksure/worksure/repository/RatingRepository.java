package com.worksure.worksure.repository;

import com.worksure.worksure.dto.WorkerRatingDTO;
import com.worksure.worksure.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByWorkerId(String workerId);

    List<Rating> findByUserId(Long userId);

    @Query("""
        SELECT new com.worksure.worksure.dto.WorkerRatingDTO(
            w.id,
            w.fullName,
            w.jobRole,
            w.preferredServiceLocation,
            AVG(r.rating)
        )
        FROM Worker w
        LEFT JOIN Rating r ON r.worker.id = w.id
        GROUP BY w.id
        ORDER BY AVG(r.rating) DESC
    """)
    List<WorkerRatingDTO> findWorkersOrderByRatingDesc();

    @Query("""
        SELECT new com.worksure.worksure.dto.WorkerRatingDTO(
            w.id,
            w.fullName,
            w.jobRole,
            w.preferredServiceLocation,
            AVG(r.rating)
        )
        FROM Worker w
        LEFT JOIN Rating r ON r.worker.id = w.id
        GROUP BY w.id
        ORDER BY AVG(r.rating) ASC
    """)
    List<WorkerRatingDTO> findWorkersOrderByRatingAsc();
}
