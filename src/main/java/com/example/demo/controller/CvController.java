package com.example.demo.controller;


import com.example.demo.DTO.*;
import com.example.demo.model.CvSocialLinks;
import com.example.demo.model.UserCV;
import com.example.demo.repository.UserCVRepository;
import com.example.demo.service.CvService;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
                                      @RequestParam(value = "summary", required = false) String summary) {

        try {
            if(! cvService.cvExists(userId))
            {
                UserCV createdCv = cvService.createNewCv(userId, name, address, summary);
                return ResponseEntity.ok(createdCv);
            }
            return ResponseEntity.status(HttpStatus.SC_OK).body("This userID already have CV in Database");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while creating the CV.");
        }
    }


    @PostMapping("/updateCvName")
    public ResponseEntity<?>  updateCvName(@RequestParam("name") String name,
                                           @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updateCvName(cvID, name);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Cv Name updated");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("This userID don't have CV");
    }


    @PostMapping("/updateCvAddress")
    public ResponseEntity<?>  updateCvAddress(@RequestParam("address") String address,
                                           @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updateCvAddress(cvID, address);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Cv Address updated");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("This userID don't have CV");
    }



    @PostMapping("/updateCvSummary")
    public ResponseEntity<?>  updateCvSummary(@RequestParam("summary") String summary,
                                              @RequestParam("cvID") int cvID)
    {
        if(cvService.cvExists(cvID))
        {
            cvService.updateCvSummary(cvID, summary);
            return ResponseEntity.status(HttpStatus.SC_OK).body("Cv summary updated");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("This userID don't have CV");
    }


    @GetMapping("/getCv/{cvID}")
    public ResponseEntity<?> getCv(@PathVariable Integer cvID)
    {
        if(cvService.cvExists(cvID))
            return ResponseEntity.status(HttpStatus.SC_OK).body(cvService.getCvByID(cvID));
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("");
    }


    @PostMapping("/addSocials")
    public ResponseEntity<?> addSocials(@RequestBody CvSocialLinksRequest socialLinksRequest) {
        if (cvService.cvExists(socialLinksRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(socialLinksRequest.getCvID());

            if (userCvOptional.isPresent()) {

                cvService.addSocial(socialLinksRequest.getCvID(), socialLinksRequest.getSocials());

                return ResponseEntity.ok("Social links added successfully.");
            }
        }

        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("CV with that ID does not exist.");
    }


    @DeleteMapping("/removeSocial/{socialID}")
    public ResponseEntity<?> removeSocialByID(@PathVariable Integer socialID){
        if(cvService.removeSocialByID(socialID))
        {
            return ResponseEntity.status(HttpStatus.SC_OK).body("Social Link with ID: "+ socialID +" removed.");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Social Link ID not found.");
    }



    @PostMapping("/addEducation")
    public ResponseEntity<?> addEducation(@RequestBody CvEducationRequest educationRequest) {
        if (cvService.cvExists(educationRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(educationRequest.getCvID());

            if (userCvOptional.isPresent()) {

                cvService.addEducation(educationRequest.getCvID(), educationRequest.getEducation());

                return ResponseEntity.ok("Education added successfully.");
            }
        }

        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("CV with that ID does not exist.");
    }


    @DeleteMapping("/removeEducation/{educationID}")
    public ResponseEntity<?> removeEducationByID(@PathVariable Integer educationID){
        if(cvService.removeEducationByID(educationID))
        {
            return ResponseEntity.status(HttpStatus.SC_OK).body("Education with ID: "+ educationID +" removed.");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Education ID not found.");
    }



    @PostMapping("/addExperience")
    public ResponseEntity<?> addExperience(@RequestBody CvExperienceRequest experienceRequest) {
        if (cvService.cvExists(experienceRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(experienceRequest.getCvID());

            if (userCvOptional.isPresent()) {

                cvService.addExperience(experienceRequest.getCvID(), experienceRequest.getExperience());

                return ResponseEntity.ok("Experience added successfully.");
            }
        }

        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("CV with that ID does not exist.");
    }


    @DeleteMapping("/removeExperience/{experienceID}")
    public ResponseEntity<?> removeExperienceByID(@PathVariable Integer experienceID){
        if(cvService.removeExperienceByID(experienceID))
        {
            return ResponseEntity.status(HttpStatus.SC_OK).body("Experience with ID: "+ experienceID +" removed.");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Experience ID not found.");
    }



    @PostMapping("/addCertificate")
    public ResponseEntity<?> addCertificates(@RequestBody CvCertificateRequest certificateRequest) {
        if (cvService.cvExists(certificateRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(certificateRequest.getCvID());

            if (userCvOptional.isPresent()) {


                cvService.addCertificate(certificateRequest.getCvID(), certificateRequest.getCertificates());

                return ResponseEntity.ok("Certificate added successfully.");
            }
        }

        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("CV with that ID does not exist.");
    }


    @DeleteMapping("/removeCertificate/{certificateID}")
    public ResponseEntity<?> removeCertificateByID(@PathVariable Integer certificateID){
        if(cvService.removeCertificateByID(certificateID))
        {
            return ResponseEntity.status(HttpStatus.SC_OK).body("Certificate with ID: "+ certificateID +" removed.");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Certificate ID not found.");
    }



    @PostMapping("/addProjects")
    public ResponseEntity<?> addProjects(@RequestBody CvProjectsRequest projectsRequest) {
        if (cvService.cvExists(projectsRequest.getCvID())) {
            Optional<UserCV> userCvOptional = cvService.getCvByID(projectsRequest.getCvID());

            if (userCvOptional.isPresent()) {


                cvService.addProjects(projectsRequest.getCvID(), projectsRequest.getProjects());

                return ResponseEntity.ok("Projects added successfully.");
            }
        }

        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("CV with that ID does not exist.");
    }


    @DeleteMapping("/removeProject/{projectID}")
    public ResponseEntity<?> removeProjectByID(@PathVariable Integer projectID){
        if(cvService.removeProjectsByID(projectID))
        {
            return ResponseEntity.status(HttpStatus.SC_OK).body("project with ID: "+ projectID +" removed.");
        }
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Project ID not found.");
    }

}
