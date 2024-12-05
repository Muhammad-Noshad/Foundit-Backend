package com.example.demo.repository;

import com.example.demo.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
    List<JobApplication> findByUser_UserId(Integer userId);

    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END " +
            "FROM JobApplication j " +
            "WHERE j.user.userId = :userId AND j.postedJob.jobId = :jobId")
    boolean existsByUserAndJob(@Param("userId") Integer userId, @Param("jobId") Integer jobId);
}
