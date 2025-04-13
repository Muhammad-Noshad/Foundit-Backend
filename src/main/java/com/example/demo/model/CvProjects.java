package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name = "cv_projects")
public class CvProjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String projectName;
    private String technologiesUsed;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_cv_id")
    @JsonBackReference
    private UserCV userCV;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserCV getUserCV() {
        return userCV;
    }

    public void setUserCV(UserCV userCV) {
        this.userCV = userCV;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologiesUsed() {
        return technologiesUsed;
    }

    public void setTechnologiesUsed(String technologiesUsed) {
        this.technologiesUsed = technologiesUsed;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
