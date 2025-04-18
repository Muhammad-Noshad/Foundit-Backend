package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "JobApplication")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    @NotNull(message = "User must be specified")
    private User user;

    @ManyToOne
    @JoinColumn(name = "JobId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Job must be specified")
    private PostedJob postedJob;

    @Column(name = "CV", nullable = false, length = 255)
    @NotBlank(message = "CV can't be blank")
    @Size(max = 255, message = "CV can't exceed 255 characters")
    private String cv;

    @Column(name = "JobSeekerComment", columnDefinition = "TEXT")
    private String jobSeekerComment;

    @Column(name = "EmployerComment", columnDefinition = "TEXT")
    private String employerComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    @NotNull(message = "Application status must be specified")
    private ApplicationStatus status = ApplicationStatus.Applied;

    @Column(name = "CreationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    public JobApplication() {}

    public JobApplication(User user, PostedJob postedJob, String cv, String jobSeekerComment, String employerComment, ApplicationStatus status) {
        this.user = user;
        this.postedJob = postedJob;
        this.cv = cv;
        this.jobSeekerComment = jobSeekerComment;
        this.employerComment = employerComment;
        this.status = status;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PostedJob getPostedJob() {
        return postedJob;
    }

    public void setPostedJob(PostedJob postedJob) {
        this.postedJob = postedJob;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getJobSeekerComment() {
        return jobSeekerComment;
    }

    public void setJobSeekerComment(String jobSeekerComment) {
        this.jobSeekerComment = jobSeekerComment;
    }

    public String getEmployerComment() {
        return employerComment;
    }

    public void setEmployerComment(String employerComment) {
        this.employerComment = employerComment;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
