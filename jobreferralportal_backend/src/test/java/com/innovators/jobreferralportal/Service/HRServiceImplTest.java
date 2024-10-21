package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;

import io.jsonwebtoken.lang.Arrays;

public class HRServiceImplTest {

	@InjectMocks
	private HRServiceImpl yourService; // Replace with your actual service class

	@Mock
	private ReferredCandidateRepo referredCandidateRepo; // Replace with your actual repository

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

}
