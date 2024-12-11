package com.example.demo.controller;

import com.example.demo.model.PostedJob;
import com.example.demo.service.CompanyService;
import com.example.demo.service.PostedJobService;
import com.example.demo.utils.Body;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{companyId}")
    public ResponseEntity<?> getPostedJobsByCompanyId(@PathVariable Integer companyId) {
        List<PostedJob> postedJobs = postedJobService.findPostedJobByCompanyId(companyId);
        return new ResponseEntity<>(postedJobs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPostedJob(@RequestBody @Valid PostedJob postedJob) {
        postedJobService.addJobPost(postedJob);
        return new ResponseEntity<>(new Body("Job created successfully!"), HttpStatus.CREATED);
    }
}
