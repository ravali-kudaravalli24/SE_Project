package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.entity.ReferredCandidate;

import java.util.List;

public interface HRService {
//    public boolean loginUserHR(String username, String password);
//
//    public void addUser(Employee employee);
    public List<ReferredCandidate> getAllReferredCandidates();
    public boolean updateStatus(String status, Long id);
    public void scoreIncrementor(Long id);
    public List<List<String>> getLeaderBoardList();
}
