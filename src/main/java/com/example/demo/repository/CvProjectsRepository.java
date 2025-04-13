package com.example.demo.repository;

import com.example.demo.model.CvProjects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvProjectsRepository extends JpaRepository<CvProjects, Integer> {
}
