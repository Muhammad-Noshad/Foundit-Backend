package com.example.demo.controller;

import com.example.demo.model.Company;
import com.example.demo.model.PostedJob;
import com.example.demo.service.CompanyService;
import com.example.demo.service.PostedJobService;
import com.example.demo.utils.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/posted-job")
public class PostedJobController {
    @Autowired
    private PostedJobService postedJobService;
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<?> getAllJobs() {
        List<PostedJob> postedJobs = postedJobService.getAllPostedJobs();
        return new ResponseEntity<>(postedJobs, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getPostedJobsById(@PathVariable Integer userId) {
        System.out.println(userId);
        Optional<Company> company = companyService.getCompanyByUserId(userId);

        if(company.isEmpty()) {
            return new ResponseEntity<>(new Body("User's company not found!"), HttpStatus.NOT_FOUND);
        }

        List<PostedJob> postedJobs = postedJobService.findPostedJobByCompanyId(company.get().getCompanyId());

        return new ResponseEntity<>(postedJobs, HttpStatus.OK);
    }
}
