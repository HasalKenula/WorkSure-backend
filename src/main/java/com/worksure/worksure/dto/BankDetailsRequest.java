package com.worksure.worksure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankDetailsRequest {
    public String fullName;
    public String email;
    public String contact;
    public String nic;
    public String bankName;
    public String accountNumber;
    public String holder;
    public String accountType;
    public String branch;
    public String branchCode;
    public String address;

    public Long userId;

    public String workerId;

}
