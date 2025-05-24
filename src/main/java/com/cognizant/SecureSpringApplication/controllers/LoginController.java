package com.cognizant.SecureSpringApplication.controllers;

import com.cognizant.SecureSpringApplication.dto.LoginRequest;
import com.cognizant.SecureSpringApplication.models.User;
import com.cognizant.SecureSpringApplication.repositories.UserRepository;
import com.cognizant.SecureSpringApplication.security.JwtUtil;
import com.cognizant.SecureSpringApplication.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired private UserRepository userRep;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
//    @Autowired private AuthenticationManager authManager;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRep.findByUsername(request.getUsername());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
//        try {
//            Authentication authentication = authManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//
//            String token = jwtUtil.generateToken(request.getUsername());
//            return ResponseEntity.ok(new JwtResponse(token));
//
//        } catch (AuthenticationException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
//        }
    }

}
