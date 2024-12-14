package com.example.demo.service;

import com.example.demo.model.Company;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CompanyService {
    void saveCompany(Company company);

    Optional<Company> findByCompanyNameAndCompanyLocation(String companyName, String companyLocation);

    boolean existsById(Integer companyId);
}
