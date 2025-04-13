package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv_experience")
public class CvExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyName;
    private String role;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;


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

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
