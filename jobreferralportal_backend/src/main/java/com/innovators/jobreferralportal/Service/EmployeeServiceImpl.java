package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private ReferredCandidateRepo referredCandidateRepo;

    @Autowired
    private JobRepo jobRepo;
    @Override
    public void referCandidate(ReferredCandidate referredCandidate) {
     referredCandidateRepo.save(referredCandidate);
    }

    @Override
    public List<Job> getAllJobs() {

        return jobRepo.findAll();
    }

    @Override
    public List<ReferredCandidate> getAllReferredCandidates() {
        return referredCandidateRepo.findAll();
    }


}
