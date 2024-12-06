package com.example.demo.service.UserImpl;

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
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Optional<Company> getCompanyByUserId(Integer userId) {
        return companyRepository.findByUser_UserId(userId);
    }
}
