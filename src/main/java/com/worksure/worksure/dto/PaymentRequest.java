package com.worksure.worksure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    public String name;
    public String address;
    public String email;
    public double amount;
    public String planName;
    public Long userId;

}
