package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    Optional<User> getUserById(Integer id);

    void deleteUserById(Integer id);

    List<User> getAllUsers();

    void saveUser(User user);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    boolean userExistsById(Integer userId);

    Optional<User> getUserByCompanyId(Integer companyId);
}
