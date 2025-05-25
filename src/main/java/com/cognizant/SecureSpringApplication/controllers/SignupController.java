package com.cognizant.SecureSpringApplication.controllers;

import com.cognizant.SecureSpringApplication.models.User;
import com.cognizant.SecureSpringApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignupController {
    @Autowired private UserService us;

    @GetMapping
    public ResponseEntity<?> getLoginPage() {
        return ResponseEntity.ok("Login Page");
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
            return ResponseEntity.badRequest().body("Username, password, and role must be provided.");
        }
        us.addUser(user);
        return ResponseEntity.ok().build();
    }

}
