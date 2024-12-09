package com.example.demo.service;

import com.example.demo.model.Company;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CompanyService {
    void addCompany(Company company);

    Optional<Company> getCompanyByUserId(Integer userId);

    Optional<Company> findByCompanyNameAndCompanyLocation(String companyName, String companyLocation);

    boolean existsById(Integer companyId);
}
