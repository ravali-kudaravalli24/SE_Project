package com.innovators.jobreferralportal.controller;


import com.innovators.jobreferralportal.Service.JobService;
import com.innovators.jobreferralportal.entity.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/HR")
public class HRController {

    @Autowired
    JobService jobService;

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
}
