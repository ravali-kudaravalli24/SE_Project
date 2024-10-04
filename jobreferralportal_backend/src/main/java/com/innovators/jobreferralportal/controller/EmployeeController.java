package com.innovators.jobreferralportal.controller;

import com.innovators.jobreferralportal.Service.EmployeeService;
import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Ref;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/referCandidate")
    public ResponseEntity<String> referCandidate(@RequestPart("resume") MultipartFile resume,
                                                 @RequestParam("fName") String fName,
                                                 @RequestParam("lName") String lName,
                                                 @RequestParam("yearsOfExp") int yearsOfExp,
                                                 @RequestParam("referredBy") int referredBy){
    // now creating referred candidate object
        try {
            ReferredCandidate candidate = ReferredCandidate.builder()
                    .fName(fName)
                    .lName(lName)
                    .yearsOfExp(yearsOfExp)
                    .referredBy((long) referredBy)
                    .resume(resume.getBytes())
                    .build();

            employeeService.referCandidate(candidate);
            return ResponseEntity.ok("Candidate referred successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error referring candidate: " + e.getMessage());
        }


    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<List<Job>> getAllJobs(){
        List<Job> opList = employeeService.getAllJobs();
        return ResponseEntity.ok(opList);
    }


    //@TODO we need to write an api that will return all the candidates referred by the employee that is logged in
    @GetMapping("/getAllReferredCandidates")
    public ResponseEntity<List<ReferredCandidate>> getAllReferredCandidates(){
        List<ReferredCandidate> opList = employeeService.getAllReferredCandidates();
        return ResponseEntity.ok(opList);
    }

}

