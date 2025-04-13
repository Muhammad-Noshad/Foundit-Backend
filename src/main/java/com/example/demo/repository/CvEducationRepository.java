package com.example.demo.repository;

import com.example.demo.model.CvEducation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvEducationRepository extends JpaRepository<CvEducation, Integer> {
}
