package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Body;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        if(!userService.existsByEmail(loginRequest.getEmail())){
            return new ResponseEntity<>(new Body("User not found!"), HttpStatus.NOT_FOUND);
        }

        if (userService.verifyUser(loginRequest.getEmail(), loginRequest.getPassword())) {
            User user = userService.findByEmail(loginRequest.getEmail());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(new Body("Invalid email or password."), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Body> register(@Valid @RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new Body("User already exists!"), HttpStatus.CONFLICT);
        }

        userService.addUser(user);
        return new ResponseEntity<>(new Body("User registered successfully!"), HttpStatus.CREATED);
    }
}

