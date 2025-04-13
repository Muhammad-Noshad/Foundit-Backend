package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "cv_social_links")
public class CvSocialLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String linkName;
    private String linkAddress;

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

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
}
