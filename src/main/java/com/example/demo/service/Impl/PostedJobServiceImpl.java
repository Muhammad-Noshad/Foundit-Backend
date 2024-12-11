package com.example.demo.service.Impl;

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

    @Override
    public List<PostedJob> findPostedJobByCompanyId(Integer companyId) {
        return postedJobRepository.findPostedJobByCompany_CompanyId(companyId);
    }

    @Override
    public void addJobPost(PostedJob postedJob) {
        postedJobRepository.save(postedJob);
    }

    @Override
    public boolean postedJobExistsById(Integer id) {
        return postedJobRepository.existsById(id);
    }

    @Override
    public void deletePostedJobById(Integer id) {
        postedJobRepository.deleteById(id);
    }
}
