package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.worksure.worksure.dto.JwtUtils;
import com.worksure.worksure.dto.UpdateUserRequest;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/user/count")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(token);

            User user = userService.getUserByUsername(username);
            if (user == null)
                return ResponseEntity.status(404).body("User not found");

            // Hide password
            user.setPassword(null);

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }

    @PostMapping("/user/update")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UpdateUserRequest req) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(token);

            User updatedUser = userService.updateUser(username, req);

            updatedUser.setPassword(null); // hide password

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        return ResponseEntity.status(200).body(user);
    }

}
