package com.cognizant.SecureSpringApplication.services;

import com.cognizant.SecureSpringApplication.models.User;
import com.cognizant.SecureSpringApplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService /*implements UserDetailsService*/ {
    @Autowired private UserRepository userRep;
    @Autowired private PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(user);
    }

    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    public User getUser(Integer id) {
        return userRep.findById(id).get();
    }

    public User getUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    public void updateUser(String username, String newUsername, String password) {
        User user = userRep.findByUsername(username);
        if (user!=null) {
            if (newUsername!=null) user.setUsername(newUsername);
            if (password!=null) user.setPassword(passwordEncoder.encode(password));
            userRep.save(user);
        }
    }

    public User deleteUser(String username) {
        User user = userRep.findByUsername(username);
        if (user==null) return null;
        userRep.deleteById(user.getId());
        return user;
    }

}
