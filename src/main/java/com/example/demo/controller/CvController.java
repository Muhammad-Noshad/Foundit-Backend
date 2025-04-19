package com.example.demo.controller;


import com.example.demo.DTO.*;
import com.example.demo.model.CvSocialLinks;
import com.example.demo.model.UserCV;
import com.example.demo.repository.UserCVRepository;
import com.example.demo.service.CvService;
import com.example.demo.utils.Body;
//import org.apache.hc.core5.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userCV")
public class CvController {
    @Autowired
    CvService cvService;

    @Autowired
    UserCVRepository userCvRepository;



    @PostMapping("/create")
    public ResponseEntity<?> createCv(@RequestParam("name") String name,
                                      @RequestParam("userID") int userId,
                                      @RequestParam(value = "address", required = false) String address,
                                      @RequestParam(value = "phone", required = false) String phone,
                                      @RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "summary", required = false) String summary) {

        try {
            if(! cvService.cvExists(userId))
            {
                UserCV createdCv = cvService.createNewCv(userId, name, address, phone, email, summary);
                return ResponseEntity.ok(createdCv);

            }
            //return ResponseEntity.status(HttpStatus.SC_OK).body("This userID already have CV in Database");
            return new ResponseEntity<>(new Body("This userID already have CV in Database"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            //return ResponseEntity.badRequest().body(e.getMessage());
            return new ResponseEntity<>(new Body(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            //return ResponseEntity.status(500).body("An error occurred while creating the CV.");
            return new ResponseEntity<>(new Body("An error occurred while creating the CV."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateCvName")
    public ResponseEntity<?>  updateCvName(@RequestParam("name") String name,
                                           @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updateCvName(cvID, name);
            return new ResponseEntity<>(new Body("Cv Name updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Body("This userID don't have CV"), HttpStatus.NOT_FOUND);
    }


    @PutMapping("/updateCvAddress")
    public ResponseEntity<?>  updateCvAddress(@RequestParam("address") String address,
                                           @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updateCvAddress(cvID, address);
            return new ResponseEntity<>(new Body("Cv Address updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Body("This userID don't have CV"), HttpStatus.NOT_FOUND);

    }



    @PutMapping("/updateCvSummary")
    public ResponseEntity<?>  updateCvSummary(@RequestParam("summary") String summary,
                                              @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updateCvSummary(cvID, summary);
            return new ResponseEntity<>(new Body("Cv summary updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Body("This userID don't have CV"), HttpStatus.NOT_FOUND);
    }



    @PutMapping("/updateCvEmail")
    public ResponseEntity<?>  updateCvEmail(@RequestParam("email") String email,
                                              @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updateEmail(cvID, email);
            return new ResponseEntity<>(new Body("Cv Email updated."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Body("This userID don't have CV."), HttpStatus.NOT_FOUND);
    }


    @PutMapping("/updateCvPhone")
    public ResponseEntity<?>  updateCvPhone(@RequestParam("phone") String phone,
                                            @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updatePhone(cvID, phone);
            return new ResponseEntity<>(new Body("Cv Phone updated."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Body("This userID don't have CV."), HttpStatus.NOT_FOUND);
    }


    @GetMapping("/getCv/{cvID}")
    public ResponseEntity<?> getCv(@PathVariable Integer cvID)
    {
        if(cvService.cvExists(cvID))
            return new ResponseEntity<>(cvService.getCvByID(cvID), HttpStatus.OK);
        return new ResponseEntity<>(new Body("This userID don't have CV."), HttpStatus.NOT_FOUND);
    }


    @PostMapping("/addSocials")
    public ResponseEntity<?> addSocials(@RequestBody CvSocialLinksRequest socialLinksRequest) {
        if (cvService.cvExists(socialLinksRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(socialLinksRequest.getCvID());

            if (userCvOptional.isPresent()) {
                cvService.addSocial(socialLinksRequest.getCvID(), socialLinksRequest.getSocials());
                return new ResponseEntity<>(new Body("Social links added successfully."), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new Body("CV with that ID does not exist."), HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/removeSocial/{socialID}")
    public ResponseEntity<?> removeSocialByID(@PathVariable Integer socialID){
        if(cvService.removeSocialByID(socialID))
            return new ResponseEntity<>(new Body("Social Link with ID: "+ socialID +" removed."), HttpStatus.OK);
        return new ResponseEntity<>(new Body("Social Link ID not found."), HttpStatus.NOT_FOUND);
    }



    @PostMapping("/addEducation")
    public ResponseEntity<?> addEducation(@RequestBody CvEducationRequest educationRequest) {
        if (cvService.cvExists(educationRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(educationRequest.getCvID());

            if (userCvOptional.isPresent()) {
                cvService.addEducation(educationRequest.getCvID(), educationRequest.getEducation());
                return new ResponseEntity<>(new Body("Education added successfully."), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Body("CV with that ID does not exist."), HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/removeEducation/{educationID}")
    public ResponseEntity<?> removeEducationByID(@PathVariable Integer educationID){

        if(cvService.removeEducationByID(educationID))
            return new ResponseEntity<>(new Body("Education with ID: "+ educationID +" removed."), HttpStatus.OK);
        return new ResponseEntity<>(new Body("Education ID not found."), HttpStatus.NOT_FOUND);
    }



    @PostMapping("/addExperience")
    public ResponseEntity<?> addExperience(@RequestBody CvExperienceRequest experienceRequest) {
        if (cvService.cvExists(experienceRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(experienceRequest.getCvID());

            if (userCvOptional.isPresent()) {
                cvService.addExperience(experienceRequest.getCvID(), experienceRequest.getExperience());
                return new ResponseEntity<>(new Body("Experience added successfully."), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Body("CV with that ID does not exist."), HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/removeExperience/{experienceID}")
    public ResponseEntity<?> removeExperienceByID(@PathVariable Integer experienceID){
        if(cvService.removeExperienceByID(experienceID))
            return new ResponseEntity<>(new Body("Experience with ID: "+ experienceID +" removed."), HttpStatus.OK);
        return new ResponseEntity<>(new Body("Experience ID not found."), HttpStatus.NOT_FOUND);
    }



    @PostMapping("/addCertificate")
    public ResponseEntity<?> addCertificates(@RequestBody CvCertificateRequest certificateRequest) {
        if (cvService.cvExists(certificateRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(certificateRequest.getCvID());

            if (userCvOptional.isPresent()) {
                cvService.addCertificate(certificateRequest.getCvID(), certificateRequest.getCertificates());
                return new ResponseEntity<>(new Body("Certificate added successfully."), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Body("CV with that ID does not exist."), HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/removeCertificate/{certificateID}")
    public ResponseEntity<?> removeCertificateByID(@PathVariable Integer certificateID){
        if(cvService.removeCertificateByID(certificateID))
            return new ResponseEntity<>(new Body("Certificate with ID: "+ certificateID +" removed."), HttpStatus.OK);
        return new ResponseEntity<>(new Body("Certificate ID not found."), HttpStatus.NOT_FOUND);
    }



    @PostMapping("/addProjects")
    public ResponseEntity<?> addProjects(@RequestBody CvProjectsRequest projectsRequest) {
        if (cvService.cvExists(projectsRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(projectsRequest.getCvID());

            if (userCvOptional.isPresent()) {
                cvService.addProjects(projectsRequest.getCvID(), projectsRequest.getProjects());
                return new ResponseEntity<>(new Body("Projects added successfully."), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new Body("CV with that ID does not exist."), HttpStatus.NOT_FOUND);


    }


    @DeleteMapping("/removeProject/{projectID}")
    public ResponseEntity<?> removeProjectByID(@PathVariable Integer projectID){
        if(cvService.removeProjectsByID(projectID))
            return new ResponseEntity<>(new Body("Project with ID: "+ projectID +" removed."), HttpStatus.OK);
        return new ResponseEntity<>(new Body("Project ID not found."), HttpStatus.NOT_FOUND);
    }



    @PostMapping("/addSkills")
    public ResponseEntity<?> addSkills(@RequestBody CvSkillRequest skillsRequest) {
        if (cvService.cvExists(skillsRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(skillsRequest.getCvID());

            if (userCvOptional.isPresent()) {

                cvService.addSkill(skillsRequest.getCvID(), skillsRequest.getSkills());
                return new ResponseEntity<>(new Body("Skills added successfully."), HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(new Body("CV with that ID does not exist."), HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("/removeSkill/{skillID}")
    public ResponseEntity<?> removeSkillByID(@PathVariable Integer skillID){
        if(cvService.removeSkillsByID(skillID))
            return new ResponseEntity<>(new Body("Skill with ID: "+ skillID +" removed."), HttpStatus.OK);
        return new ResponseEntity<>(new Body("Skill ID not found."), HttpStatus.NOT_FOUND);
    }

}
