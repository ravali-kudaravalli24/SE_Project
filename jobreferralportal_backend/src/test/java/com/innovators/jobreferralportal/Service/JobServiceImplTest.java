package com.innovators.jobreferralportal.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;

public class JobServiceImplTest {

	@InjectMocks
	private JobServiceImpl jobService;

	@Mock
	private JobRepo jobRepo;

	@Mock
	private ReferredCandidateRepo referredCandidateRepo;
	private MultipartFile multipartFile;

	@Mock
	private JobServiceImpl jobServiceImplMock;
	private ExecutorService excelParserService;

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

		Job job = new Job(1L, "Software Engineer", "Develop software applications", "IT", "3", "Dallas","Backend");
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

		Job jobListing = new Job(1L, "Software Tester", "Tests software applications", "IT", "4","New York","Backend");
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
		when(jobRepo.findByKeywordsContainingIgnoreCase(positionName)).thenReturn(expectedJobs);
		List<Job> result = jobService.searchJob(positionName);
		assertEquals(2, result.size());
		assertTrue(result.contains(job1));
		assertTrue(result.contains(job2));
	}

	@Test
	public void testSearchJob_withInvalidPositionName_shouldReturnEmptyList() {
		String positionName = "Nonexistent Position";
		when(jobRepo.findByKeywordsContainingIgnoreCase(positionName)).thenReturn(Collections.emptyList());

		List<Job> result = jobService.searchJob(positionName);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetAllJobs_ReturnsJobs() {

		Job job1 = new Job(1L, "Developer", "Software Developer", "Hybrid Role", "4","New York","Backend");
		Job job2 = new Job(2L, "Tester", "QA Tester", "Remote Job", "5", "Chicago","Backend");
		List<Job> jobList = Arrays.asList(job1, job2);

		when(jobRepo.findAll()).thenReturn(jobList);

		List<Job> result = jobService.getAllJobs();

		assertNotNull(result, "The result should not be null");
		assertEquals(2, result.size(), "The size of the list should be 2");
		assertEquals("Developer", result.get(0).getPositionName(), "The first job title should be 'Developer'");
		assertEquals("Tester", result.get(1).getPositionName(), "The second job title should be 'Tester'");
	}

	@Test
	public void testGetAllJobs_NoJobsFound() {

		when(jobRepo.findAll()).thenReturn(Collections.emptyList());

		List<Job> result = jobService.getAllJobs();

		assertNotNull(result, "The result should not be null");
		assertTrue(result.isEmpty(), "The list should be empty when no jobs are found");
	}
@Disabled
	public void testParseExcelFile_Success() throws Exception {

		MockMultipartFile multipartFile = createMockExcelMultipartFile();

		List<Job> jobList = jobService.parseExcelFile(multipartFile);

		assertNotNull(jobList, "The job list should not be null");
		assertEquals(2, jobList.size(), "There should be two jobs in the list");
		assertEquals("Software Developer", jobList.get(0).getPositionName(),
				"First job title should be 'Software Developer'");
		assertEquals("QA Tester", jobList.get(1).getPositionName(), "Second job title should be 'QA Tester'");
	}
@Disabled
	private MockMultipartFile createMockExcelMultipartFile() throws IOException {

		String excelData = "PositionName,JobDescription,DepartmentName,NumberOfOpenPositions\n"
				+ "Software Developer,Develop software,Engineering,5\n"
				+ "QA Tester,Test software,Quality Assurance,3\n";

		ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData.getBytes());

		return new MockMultipartFile("mock-file.xlsx", "mock-file.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", inputStream);
	}
@Disabled
	@Test
	public void testParseExcelFile_EmptyFile() throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();

		byte[] byteArray = outputStream.toByteArray();
		multipartFile = new MockMultipartFile("mock-file.xlsx", "mock-file.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", byteArray);
		List<Job> jobList = jobService.parseExcelFile(multipartFile);

		assertEquals(0, jobList.size());
	}
@Disabled
	@Test
	public void testSaveJobsFromExcel_ValidFile() throws Exception {
		MultipartFile file = ExcelCreationHelper();
		Job job1 = Job.builder().positionName("Software Developer").jobDescription("Developer description")
				.departmentName("IT").numberOfOpenPositions("3").build();

		Job job2 = Job.builder().positionName("QA Tester").jobDescription("QA description").departmentName("QA")
				.numberOfOpenPositions("2").build();
		List<Job> jobs = Arrays.asList(job1, job2);
		JobServiceImpl spyService = spy(jobService);
		when(spyService.parseExcelFile(file)).thenReturn(jobs);

		when(jobRepo.saveAll(jobs)).thenReturn(null);

		jobService.saveJobsFromExcel(file);

		verify(jobRepo, times(1)).saveAll(jobs);
	}
@Disabled
	public void testSaveJobsFromExcel_InvalidFileFormat() throws IOException {
		// Create an invalid Excel file that cannot be parsed
		Exception e = new Exception();
		MockMultipartFile invalidFile = new MockMultipartFile("mock-invalid-file.xlsx", "mock-invalid-file.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[] { 1, 2, 3, 4, 5 });

		when(jobService.parseExcelFile(invalidFile)).thenThrow(new IOException("Invalid file format"));

		jobService.saveJobsFromExcel(invalidFile);
		assertEquals("Invalid file format", e.getMessage());

	}
@Disabled
	public MockMultipartFile ExcelCreationHelper() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("sheet1");

		XSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Position");
		headerRow.createCell(1).setCellValue("Job Description");
		headerRow.createCell(2).setCellValue("Department");
		headerRow.createCell(3).setCellValue("Open Positions");

		// Step 3: Add job data to the sheet
		XSSFRow row1 = sheet.createRow(1);
		row1.createCell(0).setCellValue("Software Developer");
		row1.createCell(1).setCellValue("Developer description");
		row1.createCell(2).setCellValue("IT");
		row1.createCell(3).setCellValue("3");

		XSSFRow row2 = sheet.createRow(2);
		row2.createCell(0).setCellValue("QA Tester");
		row2.createCell(1).setCellValue("QA description");
		row2.createCell(2).setCellValue("QA");
		row2.createCell(3).setCellValue("2");

		workbook.write(byteArrayOutputStream);
		workbook.close();

		byte[] byteArray = byteArrayOutputStream.toByteArray();
		MockMultipartFile multipartFile = new MockMultipartFile("mock-file.xlsx", // File name
				"mock-file.xlsx", // Original file name
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // MIME type for Excel
				byteArray);
		return multipartFile;
	}
}
