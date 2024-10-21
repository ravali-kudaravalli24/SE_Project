package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;

import io.jsonwebtoken.lang.Collections;

public class EmployeeServiceImplTest {
	@Mock
	ReferredCandidateRepo referredCandidateRepo;
	private JobRepo jobRepo;
	@InjectMocks
	EmployeeServiceImpl EmployeeServiceImpl;
	private JobServiceImpl jobService;

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
				new byte[] { });

		EmployeeServiceImpl.referCandidate(referredCandidate);

		verify(referredCandidateRepo, times(1)).save((referredCandidate));
	}

	@Test
	public void testReferCandidate_Negative() {

		ReferredCandidate referredCandidate = new ReferredCandidate(null, null, "Doe", 5, 1L, "Pending",
				new byte[] {  });

		assertThrows(IllegalArgumentException.class, () -> {
			EmployeeServiceImpl.referCandidate(referredCandidate);
		});

		verify(referredCandidateRepo, never()).save(any());
	}

}
