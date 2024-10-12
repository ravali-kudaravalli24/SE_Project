package com.innovators.jobreferralportal.repository;


import com.innovators.jobreferralportal.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
Optional<Employee> findByUsername(String username);
}
