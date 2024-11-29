//package com.innovators.jobreferralportal.Controller;
//
//import com.innovators.jobreferralportal.entity.ReferredCandidate;
//import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.*;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest  // Load the full Spring context for testing
//@AutoConfigureMockMvc  // Automatically configures MockMvc
//@WithMockUser(username = "James", roles = {"HR"})
//public class HRControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;  // Inject MockMvc to perform HTTP requests
//
//    @MockBean
//    private ReferredCandidateRepo referredCandidateRepo;  // Mock the repository
//
//    @BeforeEach
//    public void setUp() {
//        // Initializes mocks before each test
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testDownloadResume_Success() throws Exception {
//        // Given: A candidate with a resume
//        Long candidateId = 1L;
//        byte[] resumeContent = "Resume Content".getBytes();  // Mock resume content
//
//        // Create a mocked ReferredCandidate object with the resume content
//        ReferredCandidate candidate = new ReferredCandidate();
//        candidate.setReferralId(candidateId);
//        candidate.setResume(resumeContent);
////        mockMvc = MockMvcBuilders.standaloneSetup(HRController)
////                .apply(springSecurity()) // This line ensures security context is applied
////                .build();
//
//        // Mock the repository to return the candidate with the resume when queried by ID
//        when(referredCandidateRepo.getReferenceById(candidateId)).thenReturn(candidate);
//
//        // When: The download endpoint is called
//        mockMvc.perform(get("/hr/downloadResume/{id}", candidateId))
//                .andExpect(status().isOk())  // Expect status 200 OK
//                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=resume_" + candidateId + ".pdf"))
//                .andExpect(content().contentType(MediaType.APPLICATION_PDF))  // Expect the content type to be PDF
//                .andExpect(content().bytes(resumeContent));  // Expect the resume content to match the mock data
//    }
//    @Test
//    public void testDownloadResume_InternalServerError() throws Exception {
//        // Given: An exception occurs during the repository call
//        Long candidateId = 1L;
//
//        // Mock the repository to throw an exception (simulating a database error)
//        when(referredCandidateRepo.getReferenceById(candidateId)).thenThrow(new RuntimeException("Database error"));
//
//        // When: The download endpoint is called
//        mockMvc.perform(get("/hr/downloadResume/{id}", candidateId))
//                .andExpect(status().isInternalServerError());  // Expect status 500 Internal Server Error
//    }
//
//
//}
