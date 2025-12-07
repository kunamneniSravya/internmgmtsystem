package com.finalproject.internMgmtSystem.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.dto.FeedbackDto;
import com.finalproject.internMgmtSystem.dto.RegisterUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void registerUser_Success() throws Exception {
        RegisterUserDto dto = new RegisterUserDto(
        		 "User1", "u@mail.com", "123",
                 Date.valueOf("2000-01-01"), "123", "Hyd", "College", "A",
                 "CSE", "TeamA", 1001L,
                 Date.valueOf("2025-01-01"), Date.valueOf("2025-12-01"),
                 2025, "resume.pdf"
        );

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists());
    }

    @Test
    void registerUser_Failure_EmailExists() throws Exception {
        RegisterUserDto dto = new RegisterUserDto(
                "Sravya", "sravya@user.com", "123",
                Date.valueOf("2000-01-01"), "9994542", "Chennai",
                "VelTech", "A", "CSE", "Backend", 1L,
                Date.valueOf("2025-01-01"), Date.valueOf("2025-03-01"),
                2025, "resume.pdf"
        );

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTasks_Success() throws Exception {
        mockMvc.perform(get("/api/user/1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
    

    @Test
    void getTasks_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/999/tasks"))
                .andExpect(status().isNotFound());
    }
    @Test
    void debug_printTasks() throws Exception {
        mockMvc.perform(get("/api/user/1/tasks"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    System.out.println("RESPONSE: " + result.getResponse().getContentAsString());
                });
    }

    @Test
    void getPerformance_Success() throws Exception {
        mockMvc.perform(get("/api/user/1/performance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getPerformance_NotFound() throws Exception {
    	mockMvc.perform(get("/api/user/999/performance"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void getStipends_Success() throws Exception {
        mockMvc.perform(get("/api/user/1/stipends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getStipends_NotFound() throws Exception {
    	mockMvc.perform(get("/api/user/999/stipends"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void getBatchDetails_Success() throws Exception {
        mockMvc.perform(get("/api/user/batch/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchCode").value(1001));
    }

    @Test
    void getBatchDetails_NotFound() throws Exception {
        mockMvc.perform(get("/api/user/batch/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void submitFeedback_Success() throws Exception {
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setBatchCode(1001L);
        dto.setTrainerId(1L);
        dto.setTrainerName("John Miller");
        dto.setFeedback("Good");
        dto.setRating(5);

        mockMvc.perform(post("/api/user/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }


    @Test
    void submitFeedback_Failure() throws Exception {
        
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(11L);
        dto.setUserName("User");
        dto.setBatchCode(1001L);
        dto.setTrainerId(1L);
        dto.setTrainerName("John");
        dto.setFeedback("Bad");
        dto.setRating(2);

        mockMvc.perform(post("/api/user/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAttendance_Success() throws Exception {
        mockMvc.perform(get("/api/user/1/attendance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getAttendance_NotFound() throws Exception {
    	mockMvc.perform(get("/api/user/999/attendance"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(0));

    }

    @Test
    void getAllBatchesPublic_Success() throws Exception {
        mockMvc.perform(get("/api/user/batches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}