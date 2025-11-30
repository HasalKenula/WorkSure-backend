package com.worksure.worksure.service;

import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.User;

@Service
public interface UserService {
    User createUser(User user);
      User getUserByUsername(String username);
}
