package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.BankDetails;

@Service
public interface BankDetailsService {
    BankDetails createBankDetails(BankDetails bankDetails);
    List<BankDetails> getBankDetails();
}
