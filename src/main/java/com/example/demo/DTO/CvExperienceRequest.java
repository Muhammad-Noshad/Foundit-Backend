package com.example.demo.DTO;

import com.example.demo.model.CvExperience;
import com.example.demo.model.CvSocialLinks;

import java.util.List;

public class CvExperienceRequest {
    private Integer cvID;
    private List<CvExperience> experience;

    public Integer getCvID() {
        return cvID;
    }

    public void setCvID(Integer cvID) {
        this.cvID = cvID;
    }

    public List<CvExperience> getExperience() {
        return experience;
    }

    public void setExperience(List<CvExperience> experience) {
        this.experience = experience;
    }
}
