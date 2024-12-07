package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "FirstName", nullable = false, length = 50)
    @NotBlank(message = "First Name can't be blank")
    @Size(max = 50, message = "First Name can't exceed 50 characters")
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 50)
    @NotBlank(message = "Last Name can't be blank")
    @Size(max = 50, message = "Last Name can't exceed 50 characters")
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email can't exceed 100 characters")
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    @NotBlank(message = "Password can't be blank")
    @Size(min = 6, max = 255, message = "Password must be at least 6 characters and no more than 255 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    @NotNull(message = "Role is required")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = true)
    @Valid
    @JsonManagedReference
    private Company company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private Set<JobApplication> jobApplications;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
