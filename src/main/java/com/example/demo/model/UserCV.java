//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "user_cv")
//public class UserCV {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String name;
//
//    private String address;
//
//    @Column(length = 1000)
//    private String executiveSummary;
//
//    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CvSocialLinks> socials = new ArrayList<>();
//
//    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CvEducation> education = new ArrayList<>();
//
//    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CvExperience> experience = new ArrayList<>();
//
//    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CvProjects> projects = new ArrayList<>();
//
//    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CvCertificates> certificates = new ArrayList<>();
//
//    @OneToOne
//    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_cv"))
//    private User user;
//
//
//
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getExecutiveSummary() {
//        return executiveSummary;
//    }
//
//    public void setExecutiveSummary(String executiveSummary) {
//        this.executiveSummary = executiveSummary;
//    }
//
//    public List<CvSocialLinks> getSocials() {
//        return socials;
//    }
//
//    public void setSocials(List<CvSocialLinks> socials) {
//        this.socials = socials;
//    }
//
//    public List<CvEducation> getEducation() {
//        return education;
//    }
//
//    public void setEducation(List<CvEducation> education) {
//        this.education = education;
//    }
//
//    public List<CvExperience> getExperience() {
//        return experience;
//    }
//
//    public void setExperience(List<CvExperience> experience) {
//        this.experience = experience;
//    }
//
//    public List<CvProjects> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(List<CvProjects> projects) {
//        this.projects = projects;
//    }
//
//    public List<CvCertificates> getCertificates() {
//        return certificates;
//    }
//
//    public void setCertificates(List<CvCertificates> certificates) {
//        this.certificates = certificates;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//}




package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_cv")
public class UserCV {

    @Id
    private Integer id;

    private String name;

    private String address;

    @Column(length = 1000)
    private String executiveSummary;

    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CvSocialLinks> socials = new ArrayList<>();

    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CvEducation> education = new ArrayList<>();

    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CvExperience> experience = new ArrayList<>();

    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CvCertificates> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "userCV", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CvProjects> projects = new ArrayList<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_user_cv"))
    private User user;

    // Getters and Setters

    public UserCV() {}

    public UserCV(String name, String address, String executiveSummary, User user) {
        this.name = name;
        this.address = address;
        this.executiveSummary = executiveSummary;
        this.user = user;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getExecutiveSummary() {
        return executiveSummary;
    }

    public void setExecutiveSummary(String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public List<CvSocialLinks> getSocials() {
        return socials;
    }

    public void setSocials(List<CvSocialLinks> socials) {
        this.socials = socials;
    }

    public List<CvEducation> getEducation() {
        return education;
    }

    public void setEducation(List<CvEducation> education) {
        this.education = education;
    }

    public List<CvExperience> getExperience() {
        return experience;
    }

    public void setExperience(List<CvExperience> experience) {
        this.experience = experience;
    }

    public List<CvCertificates> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CvCertificates> certificates) {
        this.certificates = certificates;
    }

    public List<CvProjects> getProjects() {
        return projects;
    }

    public void setProjects(List<CvProjects> projects) {
        this.projects = projects;
    }

//    public void removeSocialById(Integer socialLinkID) {
//        if (socialLinkID == null) return;
//
//        socials.removeIf(link ->  link.getId() == socialLinkID);
//    }

}
