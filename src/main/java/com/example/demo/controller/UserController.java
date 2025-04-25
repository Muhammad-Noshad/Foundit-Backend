package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.Company;
import com.example.demo.model.PostedJob;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.CompanyService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Body;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        Optional<User> user = userService.getUserById(userId);

        if(user.isEmpty()) {
            return new ResponseEntity<>(new Body("User not found!"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer userId) {
        if (!userService.userExistsById(userId)) {
            return new ResponseEntity<>(new Body("User does not exist!"), HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(userId);

        return new ResponseEntity<>(new Body("User deleted successfully!"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> editUser(
            @RequestPart("user") String userInfoString,
            @RequestPart(value = "company", required = false) String companyInfoString,
            @RequestPart(value = "companyLogo", required = false) MultipartFile companyLogo
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> userInfoMap = null;
        Map<String, String> companyInfoMap = null;

        try {
            userInfoMap = objectMapper.readValue(userInfoString, new TypeReference<>() {
            });
            if(!(companyInfoString == null)) {
            companyInfoMap = objectMapper.readValue(companyInfoString, new TypeReference<>() {
            });
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Body("Error parsing user or company data: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        Optional<User> existingUser = userService.getUserById(Integer.parseInt(userInfoMap.get("userId")));

        if(existingUser.isEmpty()) {
            return new ResponseEntity<>(new Body("User not found!"), HttpStatus.NOT_FOUND);
        }

        if(!(companyInfoString == null) && !companyService.existsById(Integer.parseInt(companyInfoMap.get("companyId")))) {
            return new ResponseEntity<>(new Body("User's company not found!"), HttpStatus.NOT_FOUND);
        }

        String companyLogoUrl = "";
        if(!(companyLogo == null)) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(companyLogo.getBytes(), ObjectUtils.emptyMap());
            companyLogoUrl = (String) uploadResult.get("secure_url");
        }

        String newPassword = existingUser.get().getPassword().equals(userInfoMap.get("password")) ? userInfoMap.get("password") : passwordEncoder.encode(userInfoMap.get("password"));

        User user = new User(userInfoMap.get("firstName"), userInfoMap.get("lastName"), userInfoMap.get("email"),
                newPassword, Role.JobSeeker);

        if (userInfoMap.get("role").equals("Employer")) {
            user.setRole(Role.Employer);
        }
        else if (userInfoMap.get("role").equals("JobSeeker")) {
            user.setRole(Role.JobSeeker);
        }
        else {
            user.setRole(Role.Admin);
        }

        user.setUserId(Integer.parseInt(userInfoMap.get("userId")));

        Company company = null;

        if(!(companyInfoString == null)) {
            company = new Company(companyInfoMap.get("companyName"), companyInfoMap.get("companyLocation"), companyLogoUrl.isBlank()? companyInfoMap.get("companyLogo"): companyLogoUrl , companyInfoMap.get("positionInCompany"));
            company.setCompanyId(Integer.parseInt(companyInfoMap.get("companyId")));
            user.setCompany(company);
            companyService.saveCompany(company);
        }

        userService.saveUser(user);

        return new ResponseEntity<>(new Body("User edited successfully!"), HttpStatus.OK);
    }




    @GetMapping("/relatedJobs/{userId}")
    public ResponseEntity<?> getJobsRelatedToUserSkills(@PathVariable Integer userId) {
        List<PostedJob> matchedJobs = userService.findJobsRelatedToUserSkills(userId);

        if (matchedJobs.isEmpty()) {
            return new ResponseEntity<>(new Body("No matching jobs found for the user's skills."), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(matchedJobs, HttpStatus.OK);
    }



}
