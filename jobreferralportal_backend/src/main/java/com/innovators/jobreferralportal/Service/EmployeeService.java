package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface EmployeeService {
    public void referCandidate(ReferredCandidate referredCandidate) throws IOException;
    public void deleteReferral(Long id);
    public List<ReferredCandidate> getAllReferredCandidatesByEmployeeId(Long employeeId);
    public List<ReferredCandidate> getAllReferredCandidatesSearch(Long employeeId, String name);
}
