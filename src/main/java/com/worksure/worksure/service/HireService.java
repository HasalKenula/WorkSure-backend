package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Hire;


@Service
public interface HireService {
    Hire createHire(Hire hire);

    List<Hire> getAllHire();
List<Hire> getHireByWorkerId(String workerId);


}
