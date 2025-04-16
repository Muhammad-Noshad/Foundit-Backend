package com.example.demo.repository;

import com.example.demo.model.CvProjects;
import com.example.demo.model.CvSkills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvSkillsRepository extends JpaRepository<CvSkills, Integer> {
}
