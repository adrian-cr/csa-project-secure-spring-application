package com.cognizant.SecureSpringApplication.controllers;

import com.cognizant.SecureSpringApplication.models.User;
import com.cognizant.SecureSpringApplication.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired private UserService us;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(us.getAllUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #username.equals(authentication.name)")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(us.getUserByUsername(username));
    }

    @PreAuthorize("#username.equals(authentication.name)")
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(
        @PathVariable("username") String username,
        @RequestParam(name="username", required = false) String newUsername,
        @RequestParam(name="password", required = false) String password
    ) {
        if (newUsername==null && password==null) {
            return ResponseEntity.badRequest().body("No fields provided to update.");
        }
        us.updateUser(username, newUsername, password);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #username.equals(authentication.name)")
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "username") String username) {
        if (us.deleteUser(username)==null) return ResponseEntity.status(404).body("User not found.");
        return ResponseEntity.ok("Username " + username + " deleted." );
    }
}
