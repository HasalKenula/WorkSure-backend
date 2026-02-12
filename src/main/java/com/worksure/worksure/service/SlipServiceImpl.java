package com.worksure.worksure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Slip;
import com.worksure.worksure.repository.SlipRepository;

@Service
public class SlipServiceImpl implements SlipService {

    @Autowired
    private SlipRepository slipRepository;

    @Override
    public Slip createSlip(Slip slip) {
        return slipRepository.save(slip);
    }
}
