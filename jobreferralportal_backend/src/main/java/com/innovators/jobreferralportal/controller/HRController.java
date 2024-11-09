package com.innovators.jobreferralportal.controller;


import com.innovators.jobreferralportal.Service.HRService;
import com.innovators.jobreferralportal.Service.JobService;
import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/hr")
public class HRController {

    @Autowired
    private JobService jobService;

    @Autowired
    private HRService hrService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ReferredCandidateRepo referredCandidateRepo;


    private final Logger LOGGER =
            LoggerFactory.getLogger(HRController.class);

    @PostMapping("/addJob")
    public Job addJob(@RequestBody Job jobPosting) {
        LOGGER.info("Saving Job triggered");
        return jobService.addJob(jobPosting);

    }

    @PostMapping("/uploadBulkJobs")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        LOGGER.info("We are in the uploadFileBlock");
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid Excel file.");
        }

        try {
            jobService.saveJobsFromExcel(file);
            return ResponseEntity.status(HttpStatus.OK).body("Job listings have been successfully uploaded.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload job listings: " + e.getMessage());
        }
    }

    @PutMapping("/updateJob/{jobId}")
    public ResponseEntity<String> updateJob(@PathVariable Long jobId, @RequestBody Job updatedJob) {
        if (jobService.updateJob(jobId, updatedJob)) {
            return ResponseEntity.ok("Job updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found");
        }
    }

    @DeleteMapping("/deleteJob/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.ok("Job deleted successfully");

    }

    //hr needs to fetch all records
    @GetMapping("/getAllReferredCandidates")
    public ResponseEntity<List<ReferredCandidate>> getAllReferredCandidates() {
        List<ReferredCandidate> candidates = hrService.getAllReferredCandidates();
        if (candidates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(candidates);
    }


    @GetMapping("/downloadResume/{id}")
    public ResponseEntity<ByteArrayResource> downloadResume(@PathVariable Long id) {
        try {
            ReferredCandidate candidate = referredCandidateRepo.getReferenceById(id);
            if (candidate != null && candidate.getResume() != null) {
                ByteArrayResource resource = new ByteArrayResource(candidate.getResume());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=resume_" + id + ".pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateCandidateStatus(@RequestParam String statusUpdate, @RequestParam Long id){
        //Now check if Status is "ACCEPTED" now a method needs to be called
        // to increase the count for employee, this method should be using
        //pickup referredBy id(use repo) and use that to increment counter with the employeeID
        if(statusUpdate.equals("ACCEPTED")){
         hrService.scoreIncrementor(id);
        }
       if( hrService.updateStatus(statusUpdate,id)) {
           return ResponseEntity.ok("Status updated Successfully");
       }else {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update");
       }
    }

    //LeaderBoard score needs to accessible for both HR and Employee
    @GetMapping("/getLeaderBoard")
    public ResponseEntity<List<List<String>>> leaderBoardList(){
      List<List<String>> leaderBoardList =  hrService.getLeaderBoardList();
      if (leaderBoardList.isEmpty()){
          return ResponseEntity.noContent().build();
      }else{
          return ResponseEntity.ok(leaderBoardList);
      }
    }
    @GetMapping("/getAllJobs")
    public ResponseEntity<List<Job>> getAllJobs(){
        List<Job> opList = jobService.getAllJobs();
        return ResponseEntity.ok(opList);
    }


    // create login for HR
//     @PostMapping("/loginHR")
//    public ResponseEntity<String> authHRUser(@RequestParam String username, @RequestParam String password) {
//         Optional<Employee> emp = employeeRepo.findByUsername(username);
//         Employee empForCheck;
//         if (emp.isPresent()) {
//             empForCheck = emp.get();
//         } else {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
//         }
//
//         if (Objects.equals(empForCheck.getRole(), "HR")) {
//             boolean isValid = hrService.loginUserHR(username, password);
//             if (isValid) {
//                 return ResponseEntity.ok("Login Succesful");
//             } else {
//                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//             }
//         }else {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//         }
//
//     }

//     @PostMapping("/addUser")
//     public ResponseEntity<String> addUser(@RequestBody Employee employee){
//
//        hrService.addUser(employee);
//        return ResponseEntity.ok("User Successfully Added");
//     }



}
