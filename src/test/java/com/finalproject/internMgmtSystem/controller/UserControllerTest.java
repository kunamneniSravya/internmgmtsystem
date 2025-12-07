package com.finalproject.internMgmtSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.dto.FeedbackDto;
import com.finalproject.internMgmtSystem.dto.RegisterUserDto;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.repository.BatchDao;
import com.finalproject.internMgmtSystem.service.UserService;

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

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BatchDao batchDao;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private User user;
    private Task task;
    private Performance performance;
    private Stipend stipend;
    private Attendance attendance;
    private InternshipBatch batch;
    private Feedback feedback;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setUserId(1L);
        user.setUserName("User1");

        task = new Task();
        task.setId(1L);
        task.setDescription("Task");

        performance = new Performance();
        performance.setId(1L);

        stipend = new Stipend();
        stipend.setId(1L);
        stipend.setAmount(2000.0);

        attendance = new Attendance();
        attendance.setAttendanceId(1L);

        batch = new InternshipBatch();
        batch.setBatchCode(11L);
        batch.setBatchName("AI");

        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setFeedback("Good");
    }

    @Test
    void testRegisterUser() throws Exception {
        RegisterUserDto dto = new RegisterUserDto(
                "User1", "u@mail.com", "123",
                Date.valueOf("2000-01-01"), "123", "Hyd", "College", "A",
                "CSE", "TeamA", 11L,
                Date.valueOf("2025-01-01"), Date.valueOf("2025-12-01"),
                2025, "resume.pdf"
        );

        when(userService.registerUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));

        verify(userService).registerUser(any());
    }

    @Test
    void testGetTasks() throws Exception {
        when(userService.getTasks(1L)).thenReturn(List.of(task));

        mockMvc.perform(get("/api/user/1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetPerformance() throws Exception {
        when(userService.getPerformance(1L)).thenReturn(List.of(performance));

        mockMvc.perform(get("/api/user/1/performance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetStipends() throws Exception {
        when(userService.getStipendHistory(1L)).thenReturn(List.of(stipend));

        mockMvc.perform(get("/api/user/1/stipends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetBatchDetails() throws Exception {
        when(userService.getBatchDetails(11L)).thenReturn(batch);

        mockMvc.perform(get("/api/user/batch/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchCode").value(11));
    }

    @Test
    void testSubmitFeedback() throws Exception {
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setBatchCode(11L);
        dto.setTrainerId(10L);
        dto.setUserName("User1");
        dto.setTrainerName("Trainer A");
        dto.setFeedback("Good");
        dto.setRating(5);

        when(userService.submitFeedback(any())).thenReturn(feedback);

        mockMvc.perform(post("/api/user/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(userService).submitFeedback(any());
    }

    @Test
    void testGetAttendance() throws Exception {
        when(userService.getAttendance(1L)).thenReturn(List.of(attendance));

        mockMvc.perform(get("/api/user/1/attendance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testGetAllBatchesPublic() throws Exception {
        when(batchDao.findAll()).thenReturn(List.of(batch));

        mockMvc.perform(get("/api/user/batches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }


}

