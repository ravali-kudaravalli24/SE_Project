package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Job;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface JobService {
    public Job addJob(Job jobListing);
    public boolean updateJob(Long id, Job updatedJob);
    public void saveJobsFromExcel(MultipartFile file) throws IOException;
    public void deleteJob(Long id);
}
