//package com.innovators.jobreferralportal.Service;
//
//import com.innovators.jobreferralportal.entity.Employee;
//import com.innovators.jobreferralportal.repository.EmployeeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmployeeDetailsService implements UserDetailsService {
//
//    @Autowired
//    private EmployeeRepo employeeRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        Employee employee = employeeRepo.findByUsername(username);
////        return new org.springframework.security.core.userdetails.User(
////                employee.getUsername(),
////                employee.getPassword(),
////                employee.getAuthorities() 
////        );
//    }
//}
