package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface EmployeeService {
    public void referCandidate(ReferredCandidate referredCandidate) throws IOException;
    public List<Job> getAllJobs();
    public List<ReferredCandidate> getAllReferredCandidates();
}
