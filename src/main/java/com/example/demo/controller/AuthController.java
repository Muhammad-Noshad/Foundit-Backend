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
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.JwtUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        if(!userService.existsByEmail(loginRequest.getEmail())){
            return new ResponseEntity<>(new Body("User not found!"), HttpStatus.NOT_FOUND);
        }

        if (!userService.verifyUser(loginRequest.getEmail(), loginRequest.getPassword())) {
            return new ResponseEntity<>(new Body("Invalid email or password."), HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(loginRequest.getEmail());

        String token = jwtUtil.generateToken(user.getEmail(), user.getPassword());
        CookieUtil.addCookie(response, JwtUtil.TOKEN_NAME, token);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/sign-up/job-seeker")
    public ResponseEntity<Body> jobSeekerSignUp(@RequestBody @Valid User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new Body("User already exists!"), HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        return new ResponseEntity<>(new Body("User sign up successful!"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-up/job-poster")
    public ResponseEntity<Body> jobPosterSignUp(
            @RequestPart("companyLogo") MultipartFile companyLogo,
            @RequestPart("userInfo") String userInfoString,
            @RequestPart("companyInfo") String companyInfoString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> userInfoMap = objectMapper.readValue(userInfoString, Map.class);
        Map<String, String> companyInfoMap = objectMapper.readValue(companyInfoString, Map.class);

        if(userService.existsByEmail(userInfoMap.get("email"))) {
            return new ResponseEntity<>(new Body("User already exists!"), HttpStatus.CONFLICT);
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(companyLogo.getBytes(), ObjectUtils.emptyMap());
        String companyLogoUrl = (String) uploadResult.get("secure_url");

        User user = new User(userInfoMap.get("firstName"), userInfoMap.get("lastName"), userInfoMap.get("email"), userInfoMap.get("password"), Role.Employer);
        Company company = new Company(companyInfoMap.get("companyName"), companyInfoMap.get("companyLocation"), companyLogoUrl, companyInfoMap.get("positionInCompany"));

        Optional<Company> companyExists = companyService.findByCompanyNameAndCompanyLocation(company.getCompanyName(), company.getCompanyLocation());

        if(companyExists.isEmpty()){
            companyService.saveCompany(company);
            user.setCompany(company);
        }
        else {
            user.setCompany(companyExists.get());
        }

        userService.saveUser(user);

        return new ResponseEntity<>(new Body("User sign up successful!"), HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(HttpServletRequest request) {
        String token = CookieUtil.getCookieValue(request, JwtUtil.TOKEN_NAME);

        if(token == null) {
            return new ResponseEntity<>(new Body("Token not found!"), HttpStatus.NO_CONTENT);
        }

        String email = jwtUtil.getEmailFromToken(token);
        String password = jwtUtil.getPasswordFromToken(token);

        if(email == null || password == null) {
            return new ResponseEntity<>(new Body("Invalid token!"), HttpStatus.NO_CONTENT);
        }

        if(!userService.verifyUser(email, password)) {
            return new ResponseEntity<>(new Body("Invalid token credentials!"), HttpStatus.NO_CONTENT);
        }

        User user = userService.findByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        CookieUtil.deleteCookie(response, JwtUtil.TOKEN_NAME);

        return new ResponseEntity<>(new Body("Token deleted successfully!"), HttpStatus.OK);
    }
}

