package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.ApplicationStatus;
import com.example.demo.model.JobApplication;
import com.example.demo.model.PostedJob;
import com.example.demo.model.User;
import com.example.demo.service.JobApplicationService;
import com.example.demo.service.PostedJobService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/job-application")
public class JobApplicationController {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserService userService;
    @Autowired
    private PostedJobService postedJobService;
    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<Body> applyJob(
            @RequestParam("cv") MultipartFile cv,
            @RequestParam("additionalComments") String additionalComments,
            @RequestParam("userId") Integer userId,
            @RequestParam("jobId") Integer jobId) throws IOException {
        if(jobApplicationService.isAlreadyApplied(userId, jobId)) {
            return new ResponseEntity<>(new Body("Job already applied!"), HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(cv.getBytes(), ObjectUtils.emptyMap());
        String cvUrl = (String) uploadResult.get("secure_url");

        Optional<User> user = userService.getUserById(userId);

        if(user.isEmpty()) {
            return new ResponseEntity<>(new Body("User not found!"), HttpStatus.NOT_FOUND);
        }

        Optional<PostedJob> postedJob = postedJobService.getPostedJobById(jobId);

        if(postedJob.isEmpty()){
            return new ResponseEntity<>(new Body("Posted Job not found!"), HttpStatus.NOT_FOUND);
        }

        JobApplication jobApplication = new JobApplication(user.get(), postedJob.get(), cvUrl, additionalComments, "",
                ApplicationStatus.Applied);

        jobApplicationService.saveJobApplication(jobApplication);

        return new ResponseEntity<>(new Body("Job applied successfully!"), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getJobApplicationsByUserId(@PathVariable Integer userId) {
        List<JobApplication> jobApplications = jobApplicationService.getJobApplicationsByUserId(userId);
        return new ResponseEntity<>(jobApplications, HttpStatus.OK);
    }

    @GetMapping("job-post/{jobPostId}")
    public ResponseEntity<?> getJobApplicationsByJobPostId(@PathVariable Integer jobPostId) {
        List<JobApplication> jobApplications = jobApplicationService.getJobApplicationsByJobPostId(jobPostId);
        return new ResponseEntity<>(jobApplications, HttpStatus.OK);
    }

    @DeleteMapping("/{jobApplicationId}")
    public ResponseEntity<?> deleteJobApplicationById(@PathVariable Integer jobApplicationId) {
        if(!jobApplicationService.jobApplicationExistsById(jobApplicationId)) {
            return new ResponseEntity<>(new Body("Job Application does not exist!"), HttpStatus.NOT_FOUND);
        }

        jobApplicationService.deleteJobApplicationById(jobApplicationId);

        return new ResponseEntity<>(new Body("Job Application deleted successfully!"), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateJobApplicationStatus(@RequestBody Map<String, String> body) {
        Optional<JobApplication> jobApplication = jobApplicationService.getJobApplicationById(Integer.parseInt(body.get("jobApplicationId")));

        if(jobApplication.isEmpty()) {
            return new ResponseEntity<>(new Body("Job Application does not exist!"), HttpStatus.NOT_FOUND);
        }

        jobApplication.get().setEmployerComment(body.get("additionalComments"));
        if(body.get("applicationStatus").equals("Approved")) {
            jobApplication.get().setStatus(ApplicationStatus.Approved);
        }
        else {
            jobApplication.get().setStatus(ApplicationStatus.Rejected);
        }

        jobApplicationService.saveJobApplication(jobApplication.get());

        return new ResponseEntity<>(new Body("Application Status updated successfully!"), HttpStatus.OK);
    }
}
