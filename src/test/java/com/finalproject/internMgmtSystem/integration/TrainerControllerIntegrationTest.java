package com.finalproject.internMgmtSystem.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.dto.AttendanceDto;
import com.finalproject.internMgmtSystem.dto.PerformanceDto;
import com.finalproject.internMgmtSystem.dto.TaskDto;
import com.finalproject.internMgmtSystem.InternMgmtSystemApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = InternMgmtSystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")

class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private TaskDto taskDto;
    private AttendanceDto attendanceDto;
    private PerformanceDto performanceDto;

    @BeforeEach
    void setUp() {
        taskDto = new TaskDto(
                1L,
                "Integration Task",
                1L,
                "PENDING",
                Date.valueOf(LocalDate.now()),
                null
        );

        attendanceDto = new AttendanceDto(
                1L,
                1001L,
                Date.valueOf(LocalDate.now()),
                true
        );

        performanceDto = new PerformanceDto(
                1L,
                1L,
                1001L,
                "John Miller",
                "Excellent",
                95
        );
    }

    @Test
    void getBatches_success() throws Exception {
        mockMvc.perform(get("/api/trainer/batches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].batchCode").value(1001));
    }

    @Test
    void getBatches_notFound() throws Exception {
        mockMvc.perform(get("/api/trainer/batches/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addTask_success() throws Exception {
        mockMvc.perform(post("/api/trainer/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Integration Task"));
    }

    @Test
    void getTasksByTrainer_success() throws Exception {
        mockMvc.perform(get("/api/trainer/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", notNullValue()));
    }

    @Test
    void getTasksByTrainer_notFound() throws Exception {
        mockMvc.perform(get("/api/trainer/tasks/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTask_success() throws Exception {
        mockMvc.perform(put("/api/trainer/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated successfully"));
    }

    @Test
    void updateTask_notFound() throws Exception {
        mockMvc.perform(put("/api/trainer/tasks/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_success() throws Exception {
        mockMvc.perform(delete("/api/trainer/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully"));
    }

    @Test
    void deleteTask_notFound() throws Exception {
        mockMvc.perform(delete("/api/trainer/tasks/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void markAttendance_success() throws Exception {
        mockMvc.perform(post("/api/trainer/attendance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(attendanceDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.present").value(true));
    }

    @Test
    void markAttendance_futureDate() throws Exception {
        AttendanceDto future = new AttendanceDto(
                1L,
                1001L,
                Date.valueOf(LocalDate.now().plusDays(2)),
                true
        );

        mockMvc.perform(post("/api/trainer/attendance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(future)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updatePerformance_success() throws Exception {
        mockMvc.perform(post("/api/trainer/performance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(performanceDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.remarks").value("Excellent"));
    }

    @Test
    void getFeedbackByName_success() throws Exception {
        mockMvc.perform(get("/api/trainer/feedback/name/John Miller"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainerName").value("John Miller"));
    }

    @Test
    void getFeedbackByName_notFound() throws Exception {
        mockMvc.perform(get("/api/trainer/feedback/name/Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFeedbackById_success() throws Exception {
        mockMvc.perform(get("/api/trainer/feedback/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainerId").value(1));
    }

    @Test
    void getFeedbackById_notFound() throws Exception {
        mockMvc.perform(get("/api/trainer/feedback/id/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPerformanceByTrainer_success() throws Exception {
        mockMvc.perform(get("/api/trainer/performance/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainerId").value(1));
    }

    @Test
    void getPerformanceByTrainer_notFound() throws Exception {
        mockMvc.perform(get("/api/trainer/performance/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsersByTrainer_success() throws Exception {
        mockMvc.perform(get("/api/trainer/UserBatch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainerId").value(1));
    }

    @Test
    void getUsersByTrainer_empty() throws Exception {
        mockMvc.perform(get("/api/trainer/UserBatch/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}