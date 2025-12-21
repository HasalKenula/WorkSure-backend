package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksure.worksure.dto.UpdateUserRequest;
import com.worksure.worksure.entity.User;

@Service
public interface UserService {
  User createUser(User user);

  User getUserByUsername(String username);

  User updateUser(String username, UpdateUserRequest request);

  User getUserById(Long id);

  List<User> getAllUsers();
}
