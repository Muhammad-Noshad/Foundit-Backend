package com.example.demo.service.Impl;

import com.example.demo.model.CvSkills;
import com.example.demo.model.PostedJob;
import com.example.demo.model.User;
import com.example.demo.model.UserCV;
import com.example.demo.repository.CvSkillsRepository;
import com.example.demo.repository.PostedJobRepository;
import com.example.demo.repository.UserCVRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CvSkillsRepository cvSkillsRepository;

    @Autowired
    private PostedJobRepository postedJobRepository;

    @Autowired
    private UserCVRepository userCVRepository;

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean userExistsById(Integer userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public Optional<User> getUserByCompanyId(Integer companyId) {
        return userRepository.findByCompany_CompanyId(companyId);
    }



    @Override
    public List<PostedJob> findJobsRelatedToUserSkills(Integer userId) {
        Optional<UserCV> userCVOpt = userCVRepository.findById(userId);
        if (userCVOpt.isEmpty()) {
            return Collections.emptyList();
        }

        UserCV userCV = userCVOpt.get();

        Set<String> skillKeywords = new HashSet<>();

        // Extract from CvSkills
        if (userCV.getSkills() != null) {
            skillKeywords.addAll(
                    userCV.getSkills().stream()
                            .filter(skill -> skill.getSkill() != null && !skill.getSkill().isBlank())
                            .flatMap(skill -> Arrays.stream(skill.getSkill().split(",")))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet())
            );
        }

        // Extract from Projects
        if (userCV.getProjects() != null) {
            skillKeywords.addAll(
                    userCV.getProjects().stream()
                            .filter(project -> project.getTechnologiesUsed() != null && !project.getTechnologiesUsed().isBlank())
                            .flatMap(project -> Arrays.stream(project.getTechnologiesUsed().split(",")))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet())
            );
        }

        System.out.println("Final Skill Keywords: " + skillKeywords);

        if (skillKeywords.isEmpty()) {
            return Collections.emptyList();
        }

        return postedJobRepository.findAll().stream()
                .filter(job -> {
                    String jobDescription = job.getJobDescription().toLowerCase();
                    return skillKeywords.stream().anyMatch(jobDescription::contains);
                })
                .collect(Collectors.toList());
    }


}
