package com.innovators.jobreferralportal.Service;
import com.innovators.jobreferralportal.entity.Employee;
import org.springframework.stereotype.Service;


public interface UserService {
    public void addUser(Employee employee);
    public Employee findByUsername(String username);
}
