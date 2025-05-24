package com.cognizant.SecureSpringApplication.repositories;

import com.cognizant.SecureSpringApplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
