package com.example.demo.controller;

import com.example.demo.model.PostedJob;
import com.example.demo.service.CompanyService;
import com.example.demo.service.PostedJobService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
