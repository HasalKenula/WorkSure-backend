package com.worksure.worksure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequest {
    public String name;
    public String contactNumber;
    public String subject;
    public String message;
    public Long userId;
}
