package com.innovators.jobreferralportal.controller;

import com.innovators.jobreferralportal.Service.EmployeeService;
import com.innovators.jobreferralportal.Service.JobService;
import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    JobService jobService;
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
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Candidate referred successfully!");



        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error referring candidate: " + e.getMessage());
        }


    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<List<Job>> getAllJobs(){
        List<Job> opList = jobService.getAllJobs();
        return ResponseEntity.ok(opList);
    }


    @GetMapping("/getAllReferredCandidates")
    public ResponseEntity<List<ReferredCandidate>> getAllReferredCandidates(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long employeeId = (Long) session.getAttribute("employeeID");

        if (employeeId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<ReferredCandidate> opList = employeeService.getAllReferredCandidatesByEmployeeId(employeeId);
        return ResponseEntity.ok(opList);
    }



    @GetMapping("/search")
    public List<Job> searchJob(@RequestParam String positionName) {
        return jobService.searchJob(positionName);
    }
}
    @DeleteMapping("/deleteReferral/{id}")
    public void deleteReferral(@PathVariable Long id){
       employeeService.deleteReferral(id);        
    }

