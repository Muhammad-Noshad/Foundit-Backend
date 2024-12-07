package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CompanyId", nullable = false, unique = true)
    private Integer companyId;

    @Column(name = "CompanyName", nullable = false, length = 100)
    @NotBlank(message = "Company name can't be blank")
    @Size(max = 100, message = "Company name can't exceed 100 characters")
    private String companyName;

    @Column(name = "CompanyLocation", nullable = false, length = 100)
    @NotBlank(message = "Company location can't be blank")
    @Size(max = 100, message = "Company location can't exceed 100 characters")
    private String companyLocation;

    @Column(name = "CompanyLogo", length = 255)
    @Size(max = 255, message = "Company logo URL can't exceed 255 characters")
    private String companyLogo;

    @Column(name = "PositionInCompany", nullable = false, length = 50)
    @NotBlank(message = "Position in company can't be blank")
    @Size(max = 50, message = "Position in company can't exceed 50 characters")
    private String positionInCompany;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<User> users;

    public Company() {
    }

    public Company(String companyName, String companyLocation, String companyLogo, String positionInCompany) {
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companyLogo = companyLogo;
        this.positionInCompany = positionInCompany;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getPositionInCompany() {
        return positionInCompany;
    }

    public void setPositionInCompany(String positionInCompany) {
        this.positionInCompany = positionInCompany;
    }

    public Set<User> getUsers() {
        return users;
    }
}
