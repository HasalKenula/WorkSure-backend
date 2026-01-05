package com.worksure.worksure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.BankDetails;
import com.worksure.worksure.repository.BankDetailsRepository;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {
    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    @Override
    public BankDetails createBankDetails(BankDetails bankDetails) {
        return bankDetailsRepository.save(bankDetails);
    }

    @Override
    public List<BankDetails> getBankDetails() {
        return bankDetailsRepository.findAll();
    }

    @Override
    public List<BankDetails> getBankByUserId(Long userId) {
        return bankDetailsRepository.findByUserId(userId).orElse(null);
    }

}
