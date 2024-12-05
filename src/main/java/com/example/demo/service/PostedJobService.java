package com.example.demo.service;

import com.example.demo.model.PostedJob;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostedJobService {
    List<PostedJob> getAllPostedJobs();

    Optional<PostedJob> getPostedJobById(Integer id);
}
