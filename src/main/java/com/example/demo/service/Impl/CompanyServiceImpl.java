package com.example.demo.service.Impl;

import com.example.demo.model.Company;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Optional<Company> findByCompanyNameAndCompanyLocation(String companyName, String companyLocation) {
        return companyRepository.findByCompanyNameAndCompanyLocation(companyName, companyLocation);
    }

    @Override
    public boolean existsById(Integer companyId) {
        return companyRepository.existsById(companyId);
    }

    @Override
    public Optional<Company> getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId);
    }
}
