package com.worksure.worksure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Hire;

import com.worksure.worksure.repository.HireRepository;

@Service
public class HireServiceImpl implements HireService{
    @Autowired
    private HireRepository hireRepository;

    @Override
    public Hire createHire(Hire hire){
       return hireRepository.save(hire);  
    }

    @Override
    public List<Hire> getAllHire(){
        return hireRepository.findAll();
    }

    @Override
public List<Hire> getHireByWorkerId(String workerId) {
    return hireRepository.findByWorker_Id(workerId);
}

 

}
