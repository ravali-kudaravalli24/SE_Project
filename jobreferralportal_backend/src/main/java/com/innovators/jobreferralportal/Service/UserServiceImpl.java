package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Change logger to use the correct class
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void addUser(Employee employee) {
        String hashedPassword = passwordEncoder.encode(employee.getPassword());
        LOGGER.info("Hashed password: {}", hashedPassword); // Use parameterized logging
        employee.setPassword(hashedPassword);
        employeeRepo.save(employee);
    }

    @Override
    public Employee findByUsername(String username)  {
        return employeeRepo.findByUsername(username);
    }

    /*
    @Override
    public boolean loginUserHR(String username, String password) {
        Optional<Employee> hrByUsername = employeeRepo.findByUsername(username);
        if (hrByUsername.isPresent()) {
            Employee existingHR = hrByUsername.get();
            return passwordEncoder.matches(password, existingHR.getPassword());
        }
        return false;
    }
    */
}
