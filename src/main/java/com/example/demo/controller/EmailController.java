package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> emailMap) {
        if(emailMap.containsKey("toEmail")) {
            emailService.sendEmail(emailMap.get("userEmail"), emailMap.get("toEmail"), emailMap.get("subject"), emailMap.get("body"));
            return new ResponseEntity<>(new Body("Email sent successfully!"), HttpStatus.OK);
        }

        Optional<User> user = userService.getUserByCompanyId(Integer.parseInt(emailMap.get("companyId")));

        if(user.isEmpty()) {
            return new ResponseEntity<>(new Body("Company not found!"), HttpStatus.NOT_FOUND);
        }

        emailService.sendEmail(emailMap.get("userEmail"), user.get().getEmail(), emailMap.get("subject"), emailMap.get("body"));

        return new ResponseEntity<>(new Body("Email sent successfully!"), HttpStatus.OK);
    }
}
