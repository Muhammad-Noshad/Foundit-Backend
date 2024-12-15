package com.example.demo.controller;

import com.example.demo.service.EmailService;
import com.example.demo.utils.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> emailMap) {
        emailService.sendEmail(emailMap.get("from"), emailMap.get("to"), emailMap.get("subject"), emailMap.get("body"));

        return new ResponseEntity<>(new Body("Email sent successfully!"), HttpStatus.OK);
    }
}
