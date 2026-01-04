package com.worksure.worksure.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worksure.worksure.entity.Hire;


@Repository
public interface HireRepository extends JpaRepository<Hire, Long> {

    List<Hire> findByWorker_Id(String workerId);
}
