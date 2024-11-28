package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Job;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JobService {
    public Job addJob(Job jobListing);
    public boolean updateJob(Long id, Job updatedJob);
    public void saveJobsFromExcel(MultipartFile file) throws IOException;
    public void deleteJob(Long id);
    public List<Job> searchJob(String positionName, String location);
    public List<Job> getAllJobs();
}
