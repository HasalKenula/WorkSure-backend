package com.worksure.worksure.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {

    public String transactionId;
    public String paymentMethod;
    public String fullName;
    public double amount;
    
    public Long userId;

    public String workerId;
}
