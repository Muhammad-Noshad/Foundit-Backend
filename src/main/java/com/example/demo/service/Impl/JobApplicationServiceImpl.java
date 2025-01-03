package com.example.demo.service.Impl;

import com.example.demo.model.JobApplication;
import com.example.demo.repository.JobApplicationRepository;
import com.example.demo.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    JobApplicationRepository jobApplicationRepository;

    @Override
    public void saveJobApplication(JobApplication jobApplication) {
        jobApplicationRepository.save(jobApplication);
    }

    @Override
    public boolean isAlreadyApplied(Integer userId, Integer postedJobId) {
        return jobApplicationRepository.existsByUserAndJob(userId, postedJobId);
    }

    @Override
    public List<JobApplication> getJobApplicationsByUserId(Integer id) {
        return jobApplicationRepository.findByUser_UserId(id);
    }

    @Override
    public boolean jobApplicationExistsById(Integer jobApplicationId) {
        return jobApplicationRepository.existsById(jobApplicationId);
    }

    @Override
    public void deleteJobApplicationById(Integer jobApplicationId) {
        jobApplicationRepository.deleteById(jobApplicationId);
    }

    @Override
    public List<JobApplication> getJobApplicationsByJobPostId(Integer jobPostId) {
        return jobApplicationRepository.findByPostedJob_JobId(jobPostId);
    }

    @Override
    public Optional<JobApplication> getJobApplicationById(Integer applicationId) {
        return jobApplicationRepository.findById(applicationId);
    }
}
