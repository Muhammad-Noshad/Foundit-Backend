package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<User> getUserById(Integer id);

    void addUser(User user);

    boolean existsByEmail(String email);

    boolean verifyUser(String email, String password);

    User findByEmail(String email);
}
