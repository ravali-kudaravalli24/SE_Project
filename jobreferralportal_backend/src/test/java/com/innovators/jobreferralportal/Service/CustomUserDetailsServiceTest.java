package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.enums.RoleEnum;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
	@Mock
	private EmployeeRepo employeeRepo;

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Test
	public void testLoadUserByUsername_UserFound() {
		String username = "dirk";
		Employee mockEmployee = new Employee();
		mockEmployee.setUsername(username);
		mockEmployee.setPassword("password123");
		mockEmployee.setRole(RoleEnum.EMPLOYEE);
		when(employeeRepo.findByUsername(username)).thenReturn(Optional.of(mockEmployee));

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		assertNotNull(userDetails);
		assertEquals(username, userDetails.getUsername());
		assertEquals("password123", userDetails.getPassword());
		}

	@Test
	public void testLoadUserByUsername_UserNotFound() {
		String username = "something";

		when(employeeRepo.findByUsername(username)).thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> {
			customUserDetailsService.loadUserByUsername(username);
		});
	}

}
