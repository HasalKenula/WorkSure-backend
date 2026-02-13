package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Slip;

@Service
public interface SlipService {
    Slip createSlip(Slip slip);

    List<Slip> getSlipByWorkerId(String workerId);

    List<Slip> getAllSlip();
}
