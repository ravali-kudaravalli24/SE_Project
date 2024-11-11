package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.enums.RoleEnum;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;

import io.jsonwebtoken.lang.Arrays;

public class HRServiceImplTest {

	@InjectMocks
	private HRServiceImpl yourService; 
	
	@Mock
	private ReferredCandidateRepo referredCandidateRepo;
	@Mock
	private EmployeeRepo employeeRepo;
	@Mock
	private JobRepo jobRepo;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllReferredCandidates_Positive() {

		byte[] sampleResume = new byte[50];
		List<ReferredCandidate> expectedCandidates = new ArrayList();
		ReferredCandidate ref1 = new ReferredCandidate(1L, "Ramya", "Madhavareddy", 5, 4L, "Active", sampleResume);
		ReferredCandidate ref2 = new ReferredCandidate(2L, "Shiva", "Kumar", 5, 4L, "Active", sampleResume);
		expectedCandidates.add(ref1);
		expectedCandidates.add(ref2);

		when(referredCandidateRepo.findAll()).thenReturn(expectedCandidates);

		List<ReferredCandidate> actualCandidates = yourService.getAllReferredCandidates();

		assertEquals(expectedCandidates, actualCandidates);
	}

	@Test
	public void testGetAllReferredCandidates_Negative() {

		List<ReferredCandidate> emptyList = new ArrayList();
		when(referredCandidateRepo.findAll()).thenReturn(emptyList);

		List<ReferredCandidate> actualCandidates = yourService.getAllReferredCandidates();

		assertEquals(actualCandidates.size(), emptyList.size());
	}

	public void testUpdateStatus_Positive() {

		Long id = 1L;
		String newStatus = "Approved";
		ReferredCandidate candidate = new ReferredCandidate(id, "John", "Doe", 5, 1L, "Pending", new byte[] {});
		when(referredCandidateRepo.findById(id)).thenReturn(Optional.of(candidate));

		boolean result = yourService.updateStatus(newStatus, id);

		assertTrue(result);
		assertEquals(newStatus, candidate.getStatus());
		verify(referredCandidateRepo, times(1)).findById(id);
	}

	@Test
	public void testUpdateStatus_Negative() {

		Long id = 2L;
		String newStatus = "Approved";
		when(referredCandidateRepo.findById(id)).thenReturn(Optional.empty());

		boolean result = yourService.updateStatus(newStatus, id);

		assertFalse(result);
		verify(referredCandidateRepo, times(1)).findById(id);
	}
//	@Test
//    public void testGetLeaderBoardList_Success() {
//        // Arrange: Mock employee data
//       
//        Employee emp1 = new Employee(1l,"Ramya","rao","ramya.doe@example.com","123-456-7890",RoleEnum.EMPLOYEE,"Software Developer","johndoe","password123",85);
//        Employee emp2 = new Employee(1l,"Sravan","Kumar","sravan.doe@example.com","123-456-7890",RoleEnum.EMPLOYEE,"Software Developer","johndoe","password123",85);
//        Employee emp3 = new Employee(1l,"Anudeep","Rao","anudeep.doe@example.com","123-456-7890",RoleEnum.EMPLOYEE,"Software Developer","johndoe","password123",85);
//        List<Employee> employees =new ArrayList<>();
//        employees.add(emp1);
//        employees.add(emp2);
//        employees.add(emp3);
//        
//
//        // Mock the repository to return the employees list
//        when(employeeRepo.findAll()).thenReturn(employees);
//
//        // Act: Call the method under test
//        List<List<String>> leaderBoard = yourService.getLeaderBoardList();
//System.out.println(leaderBoard);
//        // Assert: Verify the leaderboard is sorted by score in descending order
//        assertNotNull(leaderBoard);
//        assertEquals(2, leaderBoard.size());  // We have 3 employees
//        assertEquals(1l, leaderBoard.get(0).get(0));  // Employee with ID 2 (Jane Smith) should be first
//        assertEquals("Ramya", leaderBoard.get(0).get(1));  // Name should be "Jane,Smith"
//        assertEquals("rao", leaderBoard.get(0).get(2));  // Score should be 92
//
//           }
}
