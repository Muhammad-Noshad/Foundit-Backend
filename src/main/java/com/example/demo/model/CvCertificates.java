package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv_certificates")
public class CvCertificates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String certificateName;
    private String institute;
    private LocalDateTime dateOfCompletion;


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

    public LocalDateTime getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDateTime dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }
}
