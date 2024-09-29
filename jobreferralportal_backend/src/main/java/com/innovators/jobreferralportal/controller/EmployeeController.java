package com.innovators.jobreferralportal.controller;

import com.innovators.jobreferralportal.Service.JobService;
import com.innovators.jobreferralportal.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    JobService jobService;

    @GetMapping("/search")
    public List<Job> searchJob(@RequestParam String positionName) {
        return jobService.searchJob(positionName);
    }
}


