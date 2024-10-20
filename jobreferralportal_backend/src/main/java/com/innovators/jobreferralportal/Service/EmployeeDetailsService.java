//package com.innovators.jobreferralportal.service;
//
//import com.innovators.jobreferralportal.entity.Employee;
//import com.innovators.jobreferralportal.repository.EmployeeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class EmployeeDetailsService implements UserDetailsService {
//
//    @Autowired
//    private EmployeeRepo employeeRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Employee> emp = employeeRepo.findByUsername(username);
//        Employee employee = emp.get();
//        return new org.springframework.security.core.userdetails.User(
//                employee.getUsername(),
//                employee.getPassword(),
//                employee.getAuthorities()
//        );
//    }
//}
