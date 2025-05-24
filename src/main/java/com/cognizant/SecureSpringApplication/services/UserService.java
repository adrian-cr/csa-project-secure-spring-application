package com.cognizant.SecureSpringApplication.services;

import com.cognizant.SecureSpringApplication.models.User;
import com.cognizant.SecureSpringApplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public void updateUser(Integer id, String username, String password) {
        if (userRep.existsById(id)) {
            User user = userRep.findById(id).get();
            if (username!=null) user.setUsername(username);
            if (password!=null) user.setPassword(passwordEncoder.encode(password));
            userRep.save(user);
        }
    }

    public void deleteUser(Integer id) {
        userRep.deleteById(id);
    }

    public boolean login(String username, String rawPassword) {
        User user = userRep.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRep.findByUsername(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Collections.emptyList() // You can add authorities here if needed
//        );
//    }

}
