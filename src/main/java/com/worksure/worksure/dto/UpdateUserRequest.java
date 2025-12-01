package com.worksure.worksure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String email;
    private String contact;
    private String address;
    private String imageUrl;

    private String currentPassword;
    private String newPassword;
}
