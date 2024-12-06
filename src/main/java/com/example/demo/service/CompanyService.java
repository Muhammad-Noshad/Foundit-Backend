package com.example.demo.service;

import com.example.demo.model.Company;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    void addCompany(Company company);
}
