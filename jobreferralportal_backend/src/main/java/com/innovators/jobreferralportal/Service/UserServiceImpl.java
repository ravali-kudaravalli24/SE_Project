package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.repository.EmployeeRepo; // Ensure you import your EmployeeRepo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmployeeRepo employeeRepo; 

    @Override
    public void addUser(Employee employee) {
        employeeRepo.save(employee); 
    }
}
