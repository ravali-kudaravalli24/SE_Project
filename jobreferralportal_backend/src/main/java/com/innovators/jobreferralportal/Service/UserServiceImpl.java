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
        // Validate the employee object
        if (employee.getEmployeeID() == null || 
            employee.getFirstName() == null ||
            employee.getLastName() == null ||
            employee.getEmail() == null || 
            employee.getPhone_number() == null || 
            employee.getRole() == null || 
            employee.getPosition() == null || 
            employee.getUsername() == null || 
            employee.getPassword() == null) 
            {
            
            throw new IllegalArgumentException("All fields must be non-null.");
        }

        employeeRepo.save(employee);
    }

}
