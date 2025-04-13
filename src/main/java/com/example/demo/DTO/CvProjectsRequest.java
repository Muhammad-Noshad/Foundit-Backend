package com.example.demo.DTO;

import com.example.demo.model.CvCertificates;
import com.example.demo.model.CvProjects;

import java.util.List;

public class CvProjectsRequest {
    private Integer cvID;
    private List<CvProjects> projects;

    public Integer getCvID() {
        return cvID;
    }

    public void setCvID(Integer cvID) {
        this.cvID = cvID;
    }

    public List<CvProjects> getProjects() {
        return projects;
    }

    public void setProjects(List<CvProjects> projects) {
        this.projects = projects;
    }
}
