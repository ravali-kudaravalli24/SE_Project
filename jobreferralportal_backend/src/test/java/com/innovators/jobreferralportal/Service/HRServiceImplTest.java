package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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

import jakarta.persistence.EntityNotFoundException;

public class HRServiceImplTest {

	@InjectMocks
	private HRServiceImpl yourService;

	@Mock
	private ReferredCandidateRepo referredCandidateRepo;
	@Mock
	private EmployeeRepo employeeRepo;
	@Mock
	private JobRepo jobRepo;
	List<Employee> employeeList = new ArrayList<>();

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

	@Test
	public void testGetAllReferredCandidatesSearch_FoundCandidates() {

		ReferredCandidate candidate1 = new ReferredCandidate();
		candidate1.setFirstName("Ramya");

		ReferredCandidate candidate2 = new ReferredCandidate();
		candidate2.setFirstName("Rishika");

		List<ReferredCandidate> expectedCandidates = Arrays.asList(candidate1, candidate2);

		when(referredCandidateRepo.findByFirstNameContainingIgnoreCase("ri")).thenReturn(expectedCandidates);

		List<ReferredCandidate> actualCandidates = yourService.getAllReferredCandidatesSearch("ri");

		assertNotNull(actualCandidates);
		assertEquals(2, actualCandidates.size());
		assertTrue(actualCandidates.stream().anyMatch(c -> c.getFirstName().equals("Rishika")));
		assertTrue(actualCandidates.stream().anyMatch(c -> c.getFirstName().equals("Ramya")));
		verify(referredCandidateRepo, times(1)).findByFirstNameContainingIgnoreCase("ri");
	}

	@Test
	public void testGetAllReferredCandidatesSearch_NoCandidatesFound() {

		when(referredCandidateRepo.findByFirstNameContainingIgnoreCase("randomuser"))
				.thenReturn(Collections.emptyList());

		List<ReferredCandidate> actualCandidates = yourService.getAllReferredCandidatesSearch("randomuser");

		assertNotNull(actualCandidates);
		assertTrue(actualCandidates.isEmpty());

		verify(referredCandidateRepo, times(1)).findByFirstNameContainingIgnoreCase("randomuser");
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
		List<List<String>> leaderBoard = yourService.getLeaderBoardList();

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

		List<List<String>> leaderBoard = yourService.getLeaderBoardList();

		assertTrue(leaderBoard.isEmpty(), "Leaderboard should be empty when score is less than 1");
	}

	@Test
	public void testScoreIncrementor_Success() {
		Long referralId = 1L;
		Long referredById = 2L;

		ReferredCandidate referredCandidate = new ReferredCandidate();

		referredCandidate.setReferredBy(referredById);

		Employee employee = new Employee();
		employee.setEmployeeID(referredById);
		employee.setScore(10);
		when(referredCandidateRepo.getReferenceById(referralId)).thenReturn(referredCandidate);
		when(employeeRepo.getReferenceById(referredById)).thenReturn(employee);

		yourService.scoreIncrementor(referralId);
		assertEquals(11, employee.getScore());

		verify(referredCandidateRepo, times(1)).getReferenceById(referralId);
		verify(employeeRepo, times(1)).getReferenceById(referredById);
	}

	@Test
	public void testScoreIncrementor_EmployeeNotFound() {

		Long referralId = 1L;

		ReferredCandidate referredCandidate = new ReferredCandidate();

		referredCandidate.setReferredBy(2L);

		when(referredCandidateRepo.getReferenceById(referralId)).thenReturn(referredCandidate);
		when(employeeRepo.getReferenceById(2L)).thenThrow(new EntityNotFoundException("Employee not found"));

		assertThrows(EntityNotFoundException.class, () -> {
			yourService.scoreIncrementor(referralId);
		});

		verify(referredCandidateRepo, times(1)).getReferenceById(referralId);
		verify(employeeRepo, times(1)).getReferenceById(2L);
	}

}
