package com.innovators.jobreferralportal.controller;

import com.innovators.jobreferralportal.Service.UserService;
import com.innovators.jobreferralportal.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/addUser")  
    public ResponseEntity<String> addUser(@RequestBody Employee employee) {
        try {
            userService.addUser(employee);
            return ResponseEntity.ok("User Successfully Added");
        } catch (Exception e) {
            LOGGER.error("Error adding user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Employee employee, HttpServletRequest request) {
    try {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee authenticatedEmployee = userService.findByUsername(employee.getUsername());
        Long employeeId = authenticatedEmployee.getID(); 
        request.getSession().setAttribute("employeeId", employeeId); 
        return ResponseEntity.ok("Login successful");
    } catch (Exception e) {
        LOGGER.error("Login failed: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Invalidate the session to log the user out
            request.getSession().invalidate();
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            LOGGER.error("Error logging out user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to log out user");
        }
    }
}
