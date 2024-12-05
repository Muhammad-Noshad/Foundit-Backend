package com.example.demo.service.UserImpl;

import com.example.demo.model.PostedJob;
import com.example.demo.repository.PostedJobRepository;
import com.example.demo.service.PostedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostedJobServiceImpl implements PostedJobService {
    @Autowired
    private PostedJobRepository postedJobRepository;

    @Override
    public List<PostedJob> getAllPostedJobs() {
         return postedJobRepository.findAll();
    }

    @Override
    public Optional<PostedJob> getPostedJobById(Integer id) {
        return postedJobRepository.findById(id);
    }
}
