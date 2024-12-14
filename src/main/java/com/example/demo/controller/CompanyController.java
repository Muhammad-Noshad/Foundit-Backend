package com.example.demo.controller;

import com.example.demo.model.Company;
import com.example.demo.service.CompanyService;
import com.example.demo.utils.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable Integer companyId) {
        Optional<Company> company = companyService.getCompanyById(companyId);

        if(company.isEmpty()) {
            return new ResponseEntity<>(new Body("Company not found!"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(company.get(), HttpStatus.OK);
    }
}
