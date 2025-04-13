//package com.example.demo.service;
//
//import com.example.demo.model.*;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public interface CvService {
//    Optional<UserCV> getUserCVByUserId(Integer userId);
//
//    void saveUserCV(Integer userId, UserCV userCV);
//
//    void updateUserCV(Integer userId, UserCV updatedCV);
//
//    // Education
//    void addEducation(int userCvId, CvEducation education);
//    void removeEducation(int userCvId, int educationId);
//
//    // Experience
//    void addExperience(int userCvId, CvExperience experience);
//    void removeExperience(int userCvId, int experienceId);
//
//    // Projects
//    void addProject(int userCvId, CvProjects project);
//    void removeProject(int userCvId, int projectId);
//
//    // Certificates
//    void addCertificate(int userCvId, CvCertificates certificate);
//    void removeCertificate(int userCvId, int certificateId);
//
//    // Social Links
//    void addSocial(int userCvId, CvSocialLinks socialLink);
//    void removeSocial(int userCvId, int socialLinkId);
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
////
////// Education
////public void addEducation(CvEducation edu) {
////    education.add(edu);
////    edu.setUserCV(this);
////}
////
////public void removeEducation(CvEducation edu) {
////    education.remove(edu);
////    edu.setUserCV(null);
////}
////
////// Experience
////public void addExperience(CvExperience exp) {
////    experience.add(exp);
////    exp.setUserCV(this);
////}
////
////public void removeExperience(CvExperience exp) {
////    experience.remove(exp);
////    exp.setUserCV(null);
////}
////
////// Projects
////public void addProject(CvProjects project) {
////    projects.add(project);
////    project.setUserCV(this);
////}
////
////public void removeProject(CvProjects project) {
////    projects.remove(project);
////    project.setUserCV(null);
////}
////
////// Certificates
////public void addCertificate(CvCertificates cert) {
////    certificates.add(cert);
////    cert.setUserCV(this);
////}
////
////public void removeCertificate(CvCertificates cert) {
////    certificates.remove(cert);
////    cert.setUserCV(null);
////}
////
////// Socials
////public void addSocial(CvSocialLinks social) {
////    socials.add(social);
////    social.setUserCV(this);
////}
////
////public void removeSocial(CvSocialLinks social) {
////    socials.remove(social);
////    social.setUserCV(null);
////}





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



