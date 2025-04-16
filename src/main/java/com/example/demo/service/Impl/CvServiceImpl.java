


package com.example.demo.service.Impl;
import com.example.demo.service.CvService;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CvServiceImpl implements CvService {

    @Autowired
    UserCVRepository userCvRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CvSocialLinksRepository cvSocialLinksRepository;

    @Autowired
    CvEducationRepository cvEducationRepository;

    @Autowired
    CvExperienceRepository cvExperienceRepository;

    @Autowired
    CvCertificatesRepository cvCertificatesRepository;

    @Autowired
    CvProjectsRepository cvProjectsRepository;

    @Autowired
    CvSkillsRepository cvSkillsRepository;


    @Override
    public void updateCvAddress(Integer cvId, String address) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvId);

        if (userCVOptional.isPresent()) {
            UserCV userCV = userCVOptional.get();

            userCV.setAddress(address);

            userCvRepository.save(userCV);
        } else {
            throw new RuntimeException("UserCV not found for id: " + cvId);
        }
    }

    @Override
    public void updateCvName(Integer cvId, String name) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvId);


        if (userCVOptional.isPresent()) {
            UserCV userCV = userCVOptional.get();

            userCV.setName(name);

            userCvRepository.save(userCV);
        } else {

            throw new RuntimeException("UserCV not found for id: " + cvId);
        }
    }

    @Override
    public void updateCvSummary(Integer cvId, String summary) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvId);

        if (userCVOptional.isPresent()) {
            UserCV userCV = userCVOptional.get();

            userCV.setExecutiveSummary(summary);

            userCvRepository.save(userCV);
        } else {
            throw new RuntimeException("UserCV not found for id: " + cvId);
        }
    }

    @Override
    public void updateEmail(Integer cvId, String email) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvId);

        if (userCVOptional.isPresent()) {
            UserCV userCV = userCVOptional.get();

            userCV.setEmail(email);

            userCvRepository.save(userCV);
        } else {
            throw new RuntimeException("UserCV not found for id: " + cvId);
        }
    }

    @Override
    public void updatePhone(Integer cvId, String phone) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvId);

        if (userCVOptional.isPresent()) {
            UserCV userCV = userCVOptional.get();

            userCV.setPhone(phone);

            userCvRepository.save(userCV);
        } else {
            throw new RuntimeException("UserCV not found for id: " + cvId);
        }
    }

    @Override
    public UserCV createNewCv(Integer userId, String name, String address, String phone, String email, String summary) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: " + userId));

        if (userCvRepository.existsById(userId)) {
            throw new IllegalStateException("CV already exists for this user");
        }

        UserCV userCV = new UserCV(name, address, phone, email, summary, user);

        return userCvRepository.save(userCV);
    }

    @Override
    public boolean cvExists(Integer cvID) {
        return userCvRepository.existsById(cvID);
    }


    @Override
    public Optional<UserCV> getCvByID(Integer cvID) {
        return userCvRepository.findById(cvID);
    }


    @Override
    public void addSocial(Integer cvID, List<CvSocialLinks> socials) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvID);

        if (userCVOptional.isPresent()) {
            UserCV userCv = userCVOptional.get();

            for (CvSocialLinks link : socials) {
                link.setUserCV(userCv); // maintain bidirectional relationship
                userCv.getSocials().add(link); // modify the original collection
            }

            userCvRepository.save(userCv);
        }
    }

    @Override
    public boolean removeSocialByID(Integer socialID) {

        Optional<CvSocialLinks> socialLinkOptional = cvSocialLinksRepository.findById(socialID);

        if (socialLinkOptional.isPresent()) {
            CvSocialLinks socialLink = socialLinkOptional.get();
            UserCV userCv = socialLink.getUserCV();  // Get the associated UserCV


            userCv.getSocials().remove(socialLink);


            userCvRepository.save(userCv);
            return true;
        }
        return false;
    }


    @Override
    public void addEducation(Integer cvID, List<CvEducation> education) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvID);

        if (userCVOptional.isPresent()) {
            UserCV userCv = userCVOptional.get();

            for (CvEducation link : education) {
                link.setUserCV(userCv); // maintain bidirectional relationship
                userCv.getEducation().add(link); // modify the original collection
            }

            userCvRepository.save(userCv);
        }
    }

    @Override
    public boolean removeEducationByID(Integer educationID) {
        Optional<CvEducation> educationOptional = cvEducationRepository.findById(educationID);

        if (educationOptional.isPresent()) {
            CvEducation education = educationOptional.get();
            UserCV userCv = education.getUserCV();  // Get the associated UserCV


            userCv.getEducation().remove(education);


            userCvRepository.save(userCv);
            return true;
        }
        return false;
    }


    @Override
    public void addExperience(Integer cvID, List<CvExperience> experience) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvID);

        if (userCVOptional.isPresent()) {
            UserCV userCv = userCVOptional.get();

            for (CvExperience link : experience) {
                link.setUserCV(userCv); // maintain bidirectional relationship
                userCv.getExperience().add(link); // modify the original collection
            }

            userCvRepository.save(userCv);
        }
    }

    @Override
    public boolean removeExperienceByID(Integer experienceID) {
        Optional<CvExperience> experienceOptional = cvExperienceRepository.findById(experienceID);

        if (experienceOptional.isPresent()) {
            CvExperience experience = experienceOptional.get();
            UserCV userCv = experience.getUserCV();  // Get the associated UserCV


            userCv.getExperience().remove(experience);


            userCvRepository.save(userCv);
            return true;
        }
        return false;
    }


    @Override
    public void addCertificate(Integer cvID, List<CvCertificates> certificate) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvID);

        if (userCVOptional.isPresent()) {
            UserCV userCv = userCVOptional.get();

            for (CvCertificates link : certificate) {
                link.setUserCV(userCv); // maintain bidirectional relationship
                userCv.getCertificates().add(link); // modify the original collection
            }

            userCvRepository.save(userCv);
        }
    }

    @Override
    public boolean removeCertificateByID(Integer certificateID) {
        Optional<CvCertificates> certificateOptional = cvCertificatesRepository.findById(certificateID);

        if (certificateOptional.isPresent()) {
            CvCertificates certificates = certificateOptional.get();
            UserCV userCv = certificates.getUserCV();  // Get the associated UserCV


            userCv.getCertificates().remove(certificates);


            userCvRepository.save(userCv);
            return true;
        }
        return false;
    }


    @Override
    public void addProjects(Integer cvID, List<CvProjects> projects) {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvID);

        if (userCVOptional.isPresent()) {
            UserCV userCv = userCVOptional.get();

            for (CvProjects link : projects) {
                link.setUserCV(userCv); // maintain bidirectional relationship
                userCv.getProjects().add(link); // modify the original collection
            }

            userCvRepository.save(userCv);
        }
    }

    @Override
    public boolean removeProjectsByID(Integer projectID) {
        Optional<CvProjects> projectsOptional = cvProjectsRepository.findById(projectID);

        if (projectsOptional.isPresent()) {
            CvProjects projects = projectsOptional.get();
            UserCV userCv = projects.getUserCV();  // Get the associated UserCV


            userCv.getProjects().remove(projects);


            userCvRepository.save(userCv);
            return true;
        }
        return false;
    }


    @Override
    public void addSkill(Integer cvID, List<CvSkills> skills)
    {
        Optional<UserCV> userCVOptional = userCvRepository.findById(cvID);

        if (userCVOptional.isPresent()) {
            UserCV userCv = userCVOptional.get();

            for (CvSkills link : skills) {
                link.setUserCV(userCv); // maintain bidirectional relationship
                userCv.getSkills().add(link); // modify the original collection
            }

            userCvRepository.save(userCv);
        }
    }


    @Override
    public boolean removeSkillsByID(Integer skillID)
    {
        Optional<CvSkills> skillsOptional = cvSkillsRepository.findById(skillID);

        if (skillsOptional.isPresent()) {
            CvSkills skills = skillsOptional.get();
            UserCV userCv = skills.getUserCV();  // Get the associated UserCV


            userCv.getSkills().remove(skills);


            userCvRepository.save(userCv);
            return true;
        }
        return false;
    }

}

