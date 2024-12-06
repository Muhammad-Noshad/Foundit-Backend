package com.example.demo.repository;

import com.example.demo.model.PostedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostedJobRepository extends JpaRepository<PostedJob, Integer> {
    List<PostedJob> findPostedJobByCompany_CompanyId(Integer companyId);
}
