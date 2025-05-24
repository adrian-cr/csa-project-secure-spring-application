package com.cognizant.SecureSpringApplication.controllers;

import com.cognizant.SecureSpringApplication.models.User;
import com.cognizant.SecureSpringApplication.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService us;

    @GetMapping
    public List<User> getAllUsers() {
        return us.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return us.getUser(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        us.addUser(user);
    }

    @PostMapping("/login")


    @PutMapping("/{id}")
    public void updateUser(
        @PathVariable("id") Integer id,
        @RequestParam(name="username", required = false) String username,
        @RequestParam(name="password", required = false) String password
    ) {
        if (username==null && password==null) {
            logger.warn("No fields provided to update");
            return;
        }
        us.updateUser(id, username, password);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        us.deleteUser(id);
    }
}
