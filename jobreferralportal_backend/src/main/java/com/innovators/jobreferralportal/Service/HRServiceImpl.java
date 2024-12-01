package com.innovators.jobreferralportal.Service;


import com.innovators.jobreferralportal.controller.HRController;
import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class HRServiceImpl implements  HRService{

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private JobRepo jobRepo;
    @Autowired
    private ReferredCandidateRepo referredCandidateRepo;

    private final Logger LOGGER =
            LoggerFactory.getLogger(HRServiceImpl.class);

    @Override
    public List<ReferredCandidate> getAllReferredCandidates() {

        List<ReferredCandidate> referredCandidateList = referredCandidateRepo.findAll();
      return referredCandidateList;
    }

    @Override
    public List<ReferredCandidate> getAllReferredCandidatesSearch(String name){
        return referredCandidateRepo.findByFirstNameContainingIgnoreCase(name);
    }

    @Override
    public boolean updateStatus(String status, Long id) {
       Optional<ReferredCandidate> referredCandidate = referredCandidateRepo.findById(id);
       if(referredCandidate.isPresent()){
        ReferredCandidate candidate = referredCandidate.get();
        candidate.setStatus(status);
        referredCandidateRepo.save(candidate);
        return  true;
       }else{
           return false;
       }
    }

    @Override
    public void scoreIncrementor(Long id) {
        //getting referred candidate from repo
        ReferredCandidate referredCandidate = referredCandidateRepo.getReferenceById(id);
        Long referredByID = referredCandidate.getReferredBy();
        Employee employee = employeeRepo.getReferenceById(referredByID);
         int current_score  = employee.getScore();
         LOGGER.info("User's current score is: "+String.valueOf(current_score));
         current_score++;
         employee.setScore(current_score);
         LOGGER.info("User's score has been updated, current User Score :"+current_score);

    }

    @Override
    public List<List<String>> getLeaderBoardList() {
        List<List<String>> res = new ArrayList<>();

        // Retrieve all employees from the repository
        List<Employee> employeeList = employeeRepo.findAll();

        System.out.println("Before sorting:");
        employeeList.forEach(e -> {
            System.out.println("ID: " + e.getEmployeeID() + ", Score: " + e.getScore());
        });

        // Sort employees by score in descending order
        employeeList.sort((e1, e2) -> {
            Integer score1 = e1.getScore() == null ? 0 : e1.getScore();
            Integer score2 = e2.getScore() == null ? 0 : e2.getScore();
            return score2.compareTo(score1); // Descending order
        });

        System.out.println("After sorting:");
        employeeList.forEach(e -> {
            System.out.println("ID: " + e.getEmployeeID() + ", Score: " + e.getScore());
        });

        // Add employees to the result list only if their score is 1 or above
        for (Employee e : employeeList) {
            Integer scoreValue = e.getScore() == null ? 0 : e.getScore();
            if (scoreValue >= 1) { // Check if score is 1 or above
                String employeeName = (e.getLastName() != null ? e.getLastName() : "null") + "," +
                        (e.getFirstName() != null ? e.getFirstName() : "null");

                String score = String.valueOf(scoreValue); // Convert score to string
                String empId = String.valueOf(e.getEmployeeID());

                List<String> employeeDetailList = new ArrayList<>();
                employeeDetailList.add(empId);
                employeeDetailList.add(employeeName);
                employeeDetailList.add(score);

                res.add(employeeDetailList);
            }
        }

        return res;
    }

}
