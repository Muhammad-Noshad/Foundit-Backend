package com.example.demo.DTO;

import com.example.demo.model.CvCertificates;
import com.example.demo.model.CvEducation;

import java.util.List;

public class CvCertificateRequest {
    private Integer cvID;
    private List<CvCertificates> certificates;

    public Integer getCvID() {
        return cvID;
    }

    public void setCvID(Integer cvID) {
        this.cvID = cvID;
    }

    public List<CvCertificates> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CvCertificates> certificates) {
        this.certificates = certificates;
    }
}
