package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.enums.RoleEnum;
import com.innovators.jobreferralportal.repository.EmployeeRepo;

public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl UserServiceImpl;

	@Mock
	private EmployeeRepo employeeRepo;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddUser_Positive() {

		Employee employee = new Employee(1L, "John", "Doe", "johndoe@example.com", "1234567890", RoleEnum.EMPLOYEE,
				"Engineer", "rm678", "se@17689", 5);

		UserServiceImpl.addUser(employee);

		verify(employeeRepo, times(1)).save(employee);
	}

	@Test
	public void testAddUser_Negative() {

		Employee employee = new Employee(null, null, null, null, null, null, null, null, null, null);

		assertThrows(IllegalArgumentException.class, () -> {
			UserServiceImpl.addUser(employee);
		});

		verify(employeeRepo, never()).save(any());
	}
}
