package com.example.demo.DTO;

import com.example.demo.model.CvSkills;

import java.util.List;

public class CvSkillRequest {
    private Integer cvID;

    private List<CvSkills> skills;

    public Integer getCvID() {
        return cvID;
    }

    public void setCvID(Integer cvID) {
        this.cvID = cvID;
    }

    public List<CvSkills> getSkills() {
        return skills;
    }

    public void setSkills(List<CvSkills> cvSkills) {
        this.skills = cvSkills;
    }
}
