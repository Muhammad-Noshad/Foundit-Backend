package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.Company;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.CompanyService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Body;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private Cloudinary cloudinary;

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

    @PostMapping("/sign-up/job-seeker")
    public ResponseEntity<Body> jobSeekerSignUp(@RequestBody Map<String, String> userMap) {
        User user = new User(userMap.get("firstName"), userMap.get("lastName"), userMap.get("email"), userMap.get("password"), Role.JobSeeker);

        if (userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new Body("User already exists!"), HttpStatus.CONFLICT);
        }

        userService.addUser(user);
        return new ResponseEntity<>(new Body("User sign up successful!"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-up/job-poster")
    public ResponseEntity<Body> jobPosterSignUp(
            @RequestPart("companyLogo") MultipartFile companyLogo,
            @RequestPart("userInfo") String userInfoString,
            @RequestPart("companyInfo") String companyInfoString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> userInfoMap = null;
        Map<String, String> companyInfoMap = null;

        try {
            userInfoMap = objectMapper.readValue(userInfoString, new TypeReference<>() {
            });
            companyInfoMap = objectMapper.readValue(companyInfoString, new TypeReference<>() {
            });

        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Body("Error parsing user or company data: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        if(userService.existsByEmail(userInfoMap.get("email"))) {
            return new ResponseEntity<>(new Body("User already exists!"), HttpStatus.CONFLICT);
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(companyLogo.getBytes(), ObjectUtils.emptyMap());
        String companyLogoUrl = (String) uploadResult.get("secure_url");

        User user = new User(userInfoMap.get("firstName"), userInfoMap.get("lastName"), userInfoMap.get("email"), userInfoMap.get("password"), Role.Employer);
        Company company = new Company(companyInfoMap.get("companyName"), companyInfoMap.get("companyLocation"), companyLogoUrl, companyInfoMap.get("positionInCompany"));

        Optional<Company> companyExists = companyService.findByCompanyNameAndCompanyLocation(company.getCompanyName(), company.getCompanyLocation());

        if(companyExists.isEmpty()){
            companyService.addCompany(company);
            user.setCompany(company);
        }
        else {
            user.setCompany(companyExists.get());
        }

        userService.addUser(user);

        return new ResponseEntity<>(new Body("User sign up successful!"), HttpStatus.CREATED);
    }
}

