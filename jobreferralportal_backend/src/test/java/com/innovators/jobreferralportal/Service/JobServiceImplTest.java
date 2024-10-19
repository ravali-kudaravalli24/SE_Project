package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.repository.JobRepo;

public class JobServiceImplTest {

	@InjectMocks
	private JobServiceImpl jobService;

	@Mock
	private JobRepo jobRepo;
	private Job existingJob;
	private Job updatedJob;
	private Job job1;
	private Job job2;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		existingJob = new Job();
		existingJob.setJobId(1L);
		existingJob.setPositionName("Developer");
		existingJob.setJobDescription("Develops software");
		existingJob.setDepartmentName("IT");
		existingJob.setNumberOfOpenPositions("5");

		updatedJob = new Job();
		updatedJob.setPositionName("Senior Developer");
		updatedJob.setJobDescription("Develops software and mentors");
		updatedJob.setDepartmentName("IT");
		updatedJob.setNumberOfOpenPositions("3");

		job1 = new Job();
		job1.setJobId(1L);
		job1.setPositionName("Software Developer");

		job2 = new Job();
		job2.setJobId(2L);
		job2.setPositionName("Senior Software Developer");
	}

	@Test
	public void testAddJob_withValidJob_shouldReturnSavedJob() {

		Job job = new Job(1L, "Software Engineer", "Develop software applications", "IT", "3");
		when(jobRepo.save(job)).thenReturn(job);

		Job result = jobService.addJob(job);

		assertNotNull(result);
		assertEquals("Software Engineer", result.getPositionName());
		verify(jobRepo, times(1)).save(result);
	}

	@Test
	public void testAddJob_withNullJob_shouldThrowException() {

		Job jobListing = null;

		assertThrows(IllegalArgumentException.class, () -> {
			jobService.addJob(jobListing);
		});
	}

	@Test
	public void testAddJob_withJobThatFailsToSave_shouldThrowException() {

		Job jobListing = new Job(1L, "Software Tester", "Tests software applications", "IT", "4");
		when(jobRepo.save(jobListing)).thenThrow(new RuntimeException("Database error"));

		assertThrows(RuntimeException.class, () -> {
			jobService.addJob(jobListing);
		});
	}

	@Test
	public void testUpdateJob_withValidInput_shouldReturnTrue() {

		when(jobRepo.getReferenceById(1L)).thenReturn(existingJob);
		when(jobRepo.save(existingJob)).thenReturn(existingJob);

		boolean result = jobService.updateJob(1L, updatedJob);

		assertTrue(result);
		assertEquals("Senior Developer", existingJob.getPositionName());
		assertEquals("Develops software and mentors", existingJob.getJobDescription());
		assertEquals("3", existingJob.getNumberOfOpenPositions());
	}

	@Test
	public void testUpdateJob_withDataAccessException_shouldReturnFalse() {

		when(jobRepo.getReferenceById(1L)).thenReturn(existingJob);
		when(jobRepo.save(existingJob)).thenThrow(new DataAccessException("Database error") {
		});

		boolean result = jobService.updateJob(1L, updatedJob);

		assertFalse(result);
	}

	@Test
	public void testDeleteJob_withValidId_shouldDeleteSuccessfully() {

		Long jobId = 1L;

		jobService.deleteJob(jobId);

		verify(jobRepo, times(1)).deleteById(jobId);

		verify(jobRepo, times(1)).existsById(jobId);
	}

	@Test
	public void testDeleteJob_checkJobExists_afterDelete_shouldThrowException() {

		Long jobId = 1L;
		when(jobRepo.existsById(jobId)).thenReturn(true);

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
			jobService.deleteJob(jobId);
		});

		assertEquals("Job not deleted", thrown.getMessage());

		verify(jobRepo, times(1)).deleteById(jobId);
	}

	@Test
	public void testSearchJob_withValidPositionName_shouldReturnMatchingJobs() {
		String positionName = "Software";
		List<Job> expectedJobs = Arrays.asList(job1, job2);
		when(jobRepo.findAllByPositionNameContaining(positionName)).thenReturn(expectedJobs);
		List<Job> result = jobService.searchJob(positionName);
		assertEquals(2, result.size());
		assertTrue(result.contains(job1));
		assertTrue(result.contains(job2));
	}

	@Test
	public void testSearchJob_withInvalidPositionName_shouldReturnEmptyList() {
		String positionName = "Nonexistent Position";
		when(jobRepo.findAllByPositionNameContaining(positionName)).thenReturn(Collections.emptyList());

		List<Job> result = jobService.searchJob(positionName);

		assertTrue(result.isEmpty());
	}
	
}
