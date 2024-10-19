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
    public boolean updateStatus(String status, Long id) {
       Optional<ReferredCandidate> referredCandidate = referredCandidateRepo.findById(id);
       if(referredCandidate.isPresent()){
        ReferredCandidate candidate = referredCandidate.get();
        candidate.setStatus(status);
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
        //get all employee details with descending order of score
        List<Employee> employeeList = employeeRepo.findAll();
        //sort them based on score
       employeeList.sort(Comparator.comparingInt(Employee::getScore).reversed());
       //create a List of string with only Lname,Fname and score
        for (Employee e: employeeList){
            String employeeName = e.getLName() + "," +e.getFName();
            String score = String.valueOf(e.getScore());
            String empId = String.valueOf(e.getEmployeeID());
            List<String> employeeDetailList = new ArrayList<>();
            employeeDetailList.add(empId);
            employeeDetailList.add(employeeName);
            employeeDetailList.add(score);
            res.add(employeeDetailList);
            employeeDetailList.clear();
        }
return res;
    }


//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//    @Override
//    public boolean loginUserHR(String username, String password) {
//        Optional<Employee> HRByUsername = employeeRepo.findByUsername(username);
//        if (HRByUsername.isPresent()) {
//           Employee existingHR = HRByUsername.get();
//            return passwordEncoder.matches(password, existingHR.getPassword()); // Compare passwords
//        }
//        return false;
//    }

//    @Override
//    public void addUser(Employee employee) {
//        String hashedPassword = passwordEncoder.encode(employee.getPassword());
//        LOGGER.info(hashedPassword);
//        employee.setPassword(hashedPassword);
//        employeeRepo.save(employee);
//    }
}
