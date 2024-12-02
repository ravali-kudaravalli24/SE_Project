package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;

public class EmployeeServiceImplTest {
	@Mock
	ReferredCandidateRepo referredCandidateRepo;
	@Mock
	Employee employee;
	@Mock
	EmployeeRepo employeeRepo;

	@InjectMocks
	EmployeeServiceImpl EmployeeServiceImpl;
	List<Employee> employeeList = new ArrayList<>();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	public void testGetAllReferredCandidatesByEmployeeId_Positive() {

		Long employeeId = 1L;
		Long employeeId2 = 2L;
		Long referredByEmployee = 4L;
		byte[] sampleResume = new byte[50];
		List<ReferredCandidate> expectedCandidates = new ArrayList();
		ReferredCandidate ref1 = new ReferredCandidate(1L, "Ramya", "Madhavareddy", 5, 4L, "Active", sampleResume);
		ReferredCandidate ref2 = new ReferredCandidate(2L, "Shiva", "Kumar", 5, 4L, "Active", sampleResume);
		expectedCandidates.add(ref1);
		expectedCandidates.add(ref2);
		when(referredCandidateRepo.findByReferredBy(employeeId)).thenReturn(expectedCandidates);
		List<ReferredCandidate> actualCandidates = EmployeeServiceImpl.getAllReferredCandidatesByEmployeeId(employeeId);

		assertEquals(expectedCandidates, actualCandidates);
	}

	@Test
	public void testGetAllReferredCandidatesByEmployeeId_Negative() {

		Long employeeId = 2L;
		List<ReferredCandidate> emptyList = new ArrayList();
		when(referredCandidateRepo.findByReferredBy(employeeId)).thenReturn(emptyList);

		List<ReferredCandidate> actualCandidates = EmployeeServiceImpl.getAllReferredCandidatesByEmployeeId(employeeId);

		assertEquals(emptyList, actualCandidates);
	}

	@Test
	public void testReferCandidate_Positive() {

		ReferredCandidate referredCandidate = new ReferredCandidate(null, "John", "Doe", 5, 1L, "Pending",
				new byte[] {});

		EmployeeServiceImpl.referCandidate(referredCandidate);

		verify(referredCandidateRepo, times(1)).save((referredCandidate));
	}

	@Test
	public void testDeleteReferral_Success() {
		Long referralId = 1L;
		EmployeeServiceImpl.deleteReferral(referralId);

		verify(referredCandidateRepo, times(1)).deleteById(referralId);
	}

	@Disabled
	@Test
	public void testDeleteReferral_Fail_NotFound() {
		Long referralId = 999L;
		doThrow(new EmptyResultDataAccessException(1)).when(referredCandidateRepo).deleteById(referralId);
		try {
			EmployeeServiceImpl.deleteReferral(referralId);
		} catch (EmptyResultDataAccessException ex) {
			assertNotNull(ex);
		}

		verify(referredCandidateRepo, times(1)).deleteById(referralId);
	}

	@Test
	public void testGetAllReferredCandidatesSearch_FoundCandidates() {

		Long employeeId = 1L;
		String name = "ramya";

		ReferredCandidate candidate1 = new ReferredCandidate();
		candidate1.setFirstName("Ramya");

		ReferredCandidate candidate2 = new ReferredCandidate();
		candidate2.setFirstName("Sravan");

		List<ReferredCandidate> expectedCandidates = Arrays.asList(candidate1, candidate2);

		when(referredCandidateRepo.findByReferredByAndFirstNameContainingIgnoreCase(employeeId, name.toLowerCase()))
				.thenReturn(expectedCandidates);

		List<ReferredCandidate> actualCandidates = EmployeeServiceImpl.getAllReferredCandidatesSearch(employeeId, name);

		assertEquals(2, actualCandidates.size());
		assertTrue(actualCandidates.stream().anyMatch(c -> c.getFirstName().equals("Ramya")));
		assertTrue(actualCandidates.stream().anyMatch(c -> c.getFirstName().equals("Sravan")));

		verify(referredCandidateRepo, times(1)).findByReferredByAndFirstNameContainingIgnoreCase(employeeId,
				name.toLowerCase());
	}

	@Test
	public void testGetAllReferredCandidatesSearch_NoCandidatesFound() {

		Long employeeId = 1L;
		String name = "Ramya";
		when(referredCandidateRepo.findByReferredByAndFirstNameContainingIgnoreCase(employeeId, name.toLowerCase()))
				.thenReturn(Collections.emptyList());

		List<ReferredCandidate> actualCandidates = EmployeeServiceImpl.getAllReferredCandidatesSearch(employeeId, name);

		assertTrue(actualCandidates.isEmpty());

		verify(referredCandidateRepo, times(1)).findByReferredByAndFirstNameContainingIgnoreCase(employeeId,
				name.toLowerCase());
	}

	@Test
	public void testGetLeaderBoardList_ValidEmployees() {

		Employee emp1 = new Employee();
		emp1.setEmployeeID(1L);
		emp1.setFirstName("Ramya");
		emp1.setLastName("Madhavareddy");
		emp1.setScore(10);

		Employee emp2 = new Employee();
		emp2.setEmployeeID(2L);
		emp2.setFirstName("Rishika");
		emp2.setLastName("Dudipala");
		emp2.setScore(5);

		Employee emp3 = new Employee();
		emp3.setEmployeeID(3L);
		emp3.setFirstName("Sravan");
		emp3.setLastName("Nadipalli");
		emp3.setScore(8);

		employeeList.add(emp1);
		employeeList.add(emp2);
		employeeList.add(emp3);

		when(employeeRepo.findAll()).thenReturn(employeeList);
		List<List<String>> leaderBoard = EmployeeServiceImpl.getLeaderBoardList();

		assertEquals(3, leaderBoard.size());
		assertEquals("1", leaderBoard.get(0).get(0));
		assertEquals("Madhavareddy,Ramya", leaderBoard.get(0).get(1));
		assertEquals("10", leaderBoard.get(0).get(2));

		assertEquals("3", leaderBoard.get(1).get(0));
		assertEquals("Nadipalli,Sravan", leaderBoard.get(1).get(1));
		assertEquals("8", leaderBoard.get(1).get(2));

		assertEquals("2", leaderBoard.get(2).get(0));
		assertEquals("Dudipala,Rishika", leaderBoard.get(2).get(1));
		assertEquals("5", leaderBoard.get(2).get(2));
	}

	@Test
	public void testGetLeaderBoardList_ScoreLessThanOne() {

		Employee emp1 = new Employee();
		emp1.setEmployeeID(1L);
		emp1.setFirstName("John");
		emp1.setLastName("Doe");
		emp1.setScore(0);

		employeeList.add(emp1);

		when(employeeRepo.findAll()).thenReturn(employeeList);

		List<List<String>> leaderBoard = EmployeeServiceImpl.getLeaderBoardList();

		assertTrue(leaderBoard.isEmpty(), "Leaderboard should be empty when score is less than 1");
	}

}
