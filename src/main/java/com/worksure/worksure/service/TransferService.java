package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Transfer;

@Service
public interface TransferService {
    List<Transfer> getAllTransfer();
    Transfer createTransfer(Transfer transfer);
}
