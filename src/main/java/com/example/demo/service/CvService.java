

package com.example.demo.service;

import com.example.demo.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CvService {

    void updateCvAddress(Integer cvId, String address);
    void updateCvName(Integer cvId, String name);
    void updateCvSummary(Integer cvId, String summary);
    UserCV createNewCv(Integer userId, String name, String address, String summary);
    boolean cvExists(Integer cvID);
    Optional<UserCV> getCvByID(Integer cvID);

    void addSocial(Integer cvID,List<CvSocialLinks> socials);

    boolean removeSocialByID(Integer socialID);

    void addEducation(Integer cvID, List<CvEducation> education);

    boolean removeEducationByID(Integer educationID);

    void addExperience(Integer cvID, List<CvExperience> experience);

    boolean removeExperienceByID(Integer experienceID);

    void addCertificate(Integer cvID, List<CvCertificates> certificate);

    boolean removeCertificateByID(Integer certificateID);


    void addProjects(Integer cvID, List<CvProjects> projects);

    boolean removeProjectsByID(Integer projectID);
}



