package com.worksure.worksure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Transfer;
import com.worksure.worksure.repository.TransferRepository;

@Service
public class TransferServiceImpl implements TransferService{
    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Transfer> getAllTransfer(){
        return transferRepository.findAll();
    }

    @Override
    public Transfer createTransfer(Transfer transfer){
        return transferRepository.save(transfer);
    }
}
