package com.finalproject.internMgmtSystem.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.dto.BatchDto;
import com.finalproject.internMgmtSystem.dto.RegisterTrainerDto;
import com.finalproject.internMgmtSystem.dto.StipendDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").exists());
    }

    @Test
    void getAllTrainers() throws Exception {
        mockMvc.perform(get("/api/admin/trainers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void getAllBatches() throws Exception {
        mockMvc.perform(get("/api/admin/batches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].batchName").exists());
    }

    @Test
    void addTrainer() throws Exception {
        RegisterTrainerDto dto = new RegisterTrainerDto(
                "NewTrainer", "nt@mail.com", "pass",
                "999", "3 yrs", "Java", "Bio"
        );

        mockMvc.perform(post("/api/admin/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewTrainer"));
    }

    @Test
    void createBatch() throws Exception {
        BatchDto dto = new BatchDto(
                "ML Batch", "ML", 1L,
                Date.valueOf("2025-02-01"), Date.valueOf("2025-05-01"),
                30, 30
        );

        mockMvc.perform(post("/api/admin/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchName").value("ML Batch"));
    }

    @Test
    void giveStipend() throws Exception {
        StipendDto dto = new StipendDto(1L, "Sravya", "Cash", 5000.0);

        mockMvc.perform(post("/api/admin/stipend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(5000.0));
    }
}