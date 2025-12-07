package com.finalproject.internMgmtSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.dto.AttendanceDto;
import com.finalproject.internMgmtSystem.dto.PerformanceDto;
import com.finalproject.internMgmtSystem.dto.TaskDto;
import com.finalproject.internMgmtSystem.dto.UserBatchDto;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.service.TrainerService;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private InternshipBatch batch;
    private Task task;
    private Attendance attendance;
    private Performance performance;
    private Feedback feedback;
    private UserBatchDto userBatch;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();

        batch = new InternshipBatch();
        batch.setBatchCode(11L);
        batch.setBatchName("AI");

        task = new Task();
        task.setId(1L);
        task.setDescription("Task 1");

        attendance = new Attendance();
        attendance.setAttendanceId(1L);
        attendance.setUserId(5L);

        performance = new Performance();
        performance.setId(1L);
        performance.setUserId(5L);

        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setFeedback("Good");

        userBatch = new UserBatchDto();
        userBatch.setUserId(100L);
        userBatch.setUserName("User");
    }

    @Test
    void testGetBatches() throws Exception {
        when(trainerService.getBatches(10L)).thenReturn(List.of(batch));

        mockMvc.perform(get("/api/trainer/batches/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(trainerService).getBatches(10L);
    }

    @Test
    void testAddTask() throws Exception {
        TaskDto dto = new TaskDto(1L, "desc", 10L, "PENDING", Date.valueOf("2025-01-01"), null);
        when(trainerService.addTask(any(TaskDto.class))).thenReturn(task);

        mockMvc.perform(post("/api/trainer/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(trainerService).addTask(any(TaskDto.class));
    }

    @Test
    void testGetTasksByTrainerId() throws Exception {
        when(trainerService.getTasksByTrainerId(10L)).thenReturn(List.of(task));

        mockMvc.perform(get("/api/trainer/tasks/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(trainerService).getTasksByTrainerId(10L);
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskDto dto = new TaskDto(1L, "desc", 10L, "DONE", Date.valueOf("2025-01-01"), null);

        mockMvc.perform(put("/api/trainer/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated successfully"));

        verify(trainerService).updateTask(any(TaskDto.class), eq(1L));
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/trainer/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully"));

        verify(trainerService).deleteTask(1L);
    }
    @Test
    void testMarkAttendance() throws Exception {
        AttendanceDto dto = new AttendanceDto(1L, 11L, Date.valueOf("2025-01-01"), true);
        when(trainerService.markAttendance(any())).thenReturn(attendance);

        mockMvc.perform(post("/api/trainer/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendanceId").value(1));

        verify(trainerService).markAttendance(any());
    }

    @Test
    void testUpdatePerformance() throws Exception {
        PerformanceDto dto = new PerformanceDto(1L, 10L, 11L, "Trainer", "Good", 90);
        when(trainerService.updatePerformance(any())).thenReturn(performance);

        mockMvc.perform(post("/api/trainer/performance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(trainerService).updatePerformance(any());
    }

    @Test
    void testGetFeedbackByName() throws Exception {
        when(trainerService.viewFeedback("John")).thenReturn(List.of(feedback));

        mockMvc.perform(get("/api/trainer/feedback/name/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(trainerService).viewFeedback("John");
    }

    @Test
    void testGetFeedbackByTrainerId() throws Exception {
        when(trainerService.viewFeedbackByTrainerId(10L)).thenReturn(List.of(feedback));

        mockMvc.perform(get("/api/trainer/feedback/id/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(trainerService).viewFeedbackByTrainerId(10L);
    }

    @Test
    void testGetPerformanceByTrainerId() throws Exception {
        when(trainerService.getPerformanceByTrainerId(10L)).thenReturn(List.of(performance));

        mockMvc.perform(get("/api/trainer/performance/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(trainerService).getPerformanceByTrainerId(10L);
    }

    @Test
    void testGetUsersByTrainerId() throws Exception {
        when(trainerService.getUsersByTrainerId(10)).thenReturn(List.of(userBatch));

        mockMvc.perform(get("/api/trainer/UserBatch/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(trainerService).getUsersByTrainerId(10);
    }
}

