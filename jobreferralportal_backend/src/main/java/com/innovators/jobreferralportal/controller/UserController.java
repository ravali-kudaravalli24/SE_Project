package com.innovators.jobreferralportal.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmployeeRepo employeeRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            Optional<Employee> employeeOpt = employeeRepository.findByUsername(username);
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                HttpSession session = request.getSession(true);
                session.setAttribute("employeeID", employee.getEmployeeID());
                session.setMaxInactiveInterval(30 * 60);
                response.put("message", "Login successful");
                response.put("employeeID", employee.getEmployeeID());
                response.put("employeeType", employee.getRole().getRoleName());
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Login failed - user not found: {}", username);
                response.put("message", "User not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            logger.error("Login failed for user: {} - Error: {}", username, e.getMessage());
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("Logging out user: {}", auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        logger.info("Logout successful");
        return ResponseEntity.ok("Logout successful");
    }


    @PostMapping("/addUsers")
    public ResponseEntity<Map<String, String>> addUsers(@RequestBody Employee employee) {
        logger.info("Attempting to add user: {}", employee.getUsername());
        if (employee.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(encodedPassword);
        }
        try {
            employeeRepository.save(employee);
            logger.info("User added successfully: {}", employee.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("message", "User added successfully: " + employee.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error adding user: {} - Error: {}", employee.getUsername(), e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error adding user: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }



}
