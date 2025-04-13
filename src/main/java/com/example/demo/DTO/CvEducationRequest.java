package com.example.demo.DTO;

import com.example.demo.model.CvEducation;
import com.example.demo.model.CvSocialLinks;

import java.util.List;

public class CvEducationRequest {
    private Integer cvID;
    private List<CvEducation> education;

    public Integer getCvID() {
        return cvID;
    }

    public void setCvID(Integer cvID) {
        this.cvID = cvID;
    }

    public List<CvEducation> getEducation() {
        return education;
    }

    public void setEducation(List<CvEducation> education) {
        this.education = education;
    }
}
