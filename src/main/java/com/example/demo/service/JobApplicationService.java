package com.example.demo.service;

import com.example.demo.model.JobApplication;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface JobApplicationService {
    void addJobApplication(JobApplication jobApplication);

    boolean isAlreadyApplied(Integer userId, Integer postedJobId);

    List<JobApplication> getJobApplicationsByUserId(Integer userId);

    boolean jobApplicationExistsById(Integer jobApplicationId);

    void deleteJobApplicationById(Integer jobApplicationId);
    List<JobApplication> getJobApplicationsByJobPostId(Integer jobPostId);
}
