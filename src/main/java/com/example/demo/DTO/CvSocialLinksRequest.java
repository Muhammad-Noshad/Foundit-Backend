package com.example.demo.DTO;

import com.example.demo.model.CvSocialLinks;

import java.util.List;

public class CvSocialLinksRequest {
    private Integer cvID;
    private List<CvSocialLinks> socials;

    public Integer getCvID() {
        return cvID;
    }

    public void setCvID(Integer cvID) {
        this.cvID = cvID;
    }

    public List<CvSocialLinks> getSocials() {
        return socials;
    }

    public void setSocials(List<CvSocialLinks> socials) {
        this.socials = socials;
    }
}
