package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PostedJob")
public class PostedJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JobId", nullable = false, unique = true)
    private Integer jobId;

    @Column(name = "JobTitle", nullable = false, length = 100)
    @NotBlank(message = "Job title can't be blank")
    @Size(max = 100, message = "Job title can't exceed 100 characters")
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "JobType", nullable = false)
    @NotNull(message = "Job type must be specified")
    private JobType jobType;

    @Column(name = "JobSalary", nullable = false)
    @NotNull(message = "Job salary must be specified")
    @Min(value = 0, message = "Job salary must be a positive number")
    private Double jobSalary;

    @Column(name = "JobDescription", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Job description can't be blank")
    private String jobDescription;

    @ManyToOne
    @JoinColumn(name = "CompanyId", nullable = false)
    @NotNull(message = "Company must be specified")
    private Company company;

    @Column(name = "CreationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    public PostedJob() {
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Double getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(Double jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

