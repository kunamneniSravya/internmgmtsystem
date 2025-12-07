package com.finalproject.internMgmtSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.dto.BatchDto;
import com.finalproject.internMgmtSystem.dto.RegisterTrainerDto;
import com.finalproject.internMgmtSystem.dto.StipendDto;
import com.finalproject.internMgmtSystem.model.InternshipBatch;
import com.finalproject.internMgmtSystem.model.Stipend;
import com.finalproject.internMgmtSystem.model.Trainer;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.service.AdminService;
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

class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private ObjectMapper mapper = new ObjectMapper(); //dto-json

    private Trainer trainer;
    private InternshipBatch batch;
    private Stipend stipend;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        trainer = new Trainer();
        trainer.setTrainerId(10L);
        trainer.setName("Trainer A");
        trainer.setEmail("t@mail.com");

        batch = new InternshipBatch();
        batch.setBatchCode(101L);
        batch.setBatchName("Java Batch");
        batch.setCourseName("Java");

        stipend = new Stipend();
        stipend.setId(1L);
        stipend.setUserId(1L);
        stipend.setAmount(2000.0);

        user = new User();
        user.setUserId(5L);
        user.setUserName("User A");
        user.setEmail("user@mail.com");
    }

    @Test
    void testAddTrainer() throws Exception {
        RegisterTrainerDto dto =
                new RegisterTrainerDto("Trainer A","t@mail.com","123","123","2yrs","Java","Bio");

        when(adminService.registerTrainer(any())).thenReturn(trainer);

        mockMvc.perform(post("/api/admin/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainerId").value(10))
                .andExpect(jsonPath("$.name").value("Trainer A"));

        verify(adminService).registerTrainer(any());
    }

    @Test
    void testCreateBatch() throws Exception {
        BatchDto dto =
                new BatchDto("Java Batch","Java",10L,
                        Date.valueOf("2025-01-01"),Date.valueOf("2025-03-01"),30,5);

        when(adminService.createBatch(any())).thenReturn(batch);

        mockMvc.perform(post("/api/admin/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchCode").value(101))
                .andExpect(jsonPath("$.batchName").value("Java Batch"));

        verify(adminService).createBatch(any());
    }

    @Test
    void testAddStipend() throws Exception {
        StipendDto dto = new StipendDto(1L,"User","Cash",8000.0);

        when(adminService.giveStipend(any(StipendDto.class)))
        .thenAnswer(invocation -> {
            StipendDto input = invocation.getArgument(0);
            Stipend s = new Stipend();
            s.setUserId(input.getUserId());
            s.setUserName(input.getUserName());
            s.setPaymentMode(input.getPaymentMode());
            s.setAmount(input.getAmount());
            return s;
        });

        mockMvc.perform(post("/api/admin/stipend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(8000.0));

        verify(adminService).giveStipend(any());
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(adminService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(adminService).getAllUsers();
    }

    @Test
    void testGetAllUsers_Empty() throws Exception {
        when(adminService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        verify(adminService).getAllUsers();
    }

    @Test
    void testGetAllTrainers() throws Exception {
        when(adminService.getAllTrainers()).thenReturn(List.of(trainer));

        mockMvc.perform(get("/api/admin/trainers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(adminService).getAllTrainers();
    }
    
    @Test
    void testGetAllTrainers_Empty() throws Exception {
        when(adminService.getAllTrainers()).thenReturn(List.of());

        mockMvc.perform(get("/api/admin/trainers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        verify(adminService).getAllTrainers();
    }

    @Test
    void testGetAllBatches() throws Exception {
        when(adminService.getAllBatches()).thenReturn(List.of(batch));

        mockMvc.perform(get("/api/admin/batches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(adminService).getAllBatches();
    }
    
    @Test
    void testGetAllBatches_Empty() throws Exception {
        when(adminService.getAllBatches()).thenReturn(List.of());

        mockMvc.perform(get("/api/admin/batches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        verify(adminService).getAllBatches();
    }


    @Test
    void testDeleteUserByEmail() throws Exception {
        doNothing().when(adminService).deleteUserByEmail("user@mail.com");

        mockMvc.perform(delete("/api/admin/user/email/user@mail.com"))
                .andExpect(status().isOk());

        verify(adminService).deleteUserByEmail("user@mail.com");
    }

    @Test
    void testDeleteTrainerByEmail() throws Exception {
        doNothing().when(adminService).deleteTrainerByEmail("t@mail.com");

        mockMvc.perform(delete("/api/admin/trainer/email/t@mail.com"))
                .andExpect(status().isOk());

        verify(adminService).deleteTrainerByEmail("t@mail.com");
    }

    @Test
    void testDeleteBatchByName() throws Exception {
        doNothing().when(adminService).deleteBatchByName("Java Batch");

        mockMvc.perform(delete("/api/admin/batch/name/Java Batch"))
                .andExpect(status().isOk());

        verify(adminService).deleteBatchByName("Java Batch");
    }
}

