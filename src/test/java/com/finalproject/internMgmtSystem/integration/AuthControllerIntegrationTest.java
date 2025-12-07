package com.finalproject.internMgmtSystem.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.InternMgmtSystemApplication;
import com.finalproject.internMgmtSystem.dto.CreateAdminDto;
import com.finalproject.internMgmtSystem.dto.LoginRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = InternMgmtSystemApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testLogin_UserSuccess() throws Exception {
        LoginRequest req = new LoginRequest("sravya@user.com", "pass123"); 

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testLogin_WrongPassword() throws Exception {
        LoginRequest req = new LoginRequest("sravya@user.com", "wrong");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testLogin_UserNotFound() throws Exception {
        LoginRequest req = new LoginRequest("no@mail.com", "123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

//    @Test
//    void testCreateAdmin_Success() throws Exception {
//        CreateAdminDto dto = new CreateAdminDto();
//        dto.setUserName("Admin User");
//        dto.setEmail("newadmin@mail.com");
//        dto.setPassword("admin123");
//
//        mockMvc.perform(post("/api/auth/create-admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Admin created successfully!"));
//        System.out.println(mapper.writeValueAsString(dto));
//    }

    @Test
    void testCreateAdmin_AlreadyExists() throws Exception {
        CreateAdminDto dto = new CreateAdminDto();
        dto.setUserName("Test Admin");
        dto.setEmail("existing@admin.com");
        dto.setPassword("123");

        mockMvc.perform(post("/api/auth/create-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateAdmin_EmailAlreadyUsed() throws Exception {

        CreateAdminDto dto = new CreateAdminDto();
        dto.setUserName("Admin");
        dto.setEmail("sravya@user.com"); // Already added in data.sql
        dto.setPassword("123");

        mockMvc.perform(post("/api/auth/create-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}