package com.worksure.worksure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.worksure.worksure.dto.UpdateUserRequest;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User updateUser(String username, UpdateUserRequest req) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update basic fields
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setContact(req.getContact());
        user.setAddress(req.getAddress());
        user.setImageUrl(req.getImageUrl());

        // If password change requested
        if (req.getCurrentPassword() != null && !req.getCurrentPassword().isEmpty()) {

            boolean matches = passwordEncoder.matches(req.getCurrentPassword(), user.getPassword());
            if (!matches) {
                throw new RuntimeException("Current password incorrect");
            }

            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
