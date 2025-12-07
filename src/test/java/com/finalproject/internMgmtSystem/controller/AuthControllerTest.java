package com.finalproject.internMgmtSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.internMgmtSystem.dto.CreateAdminDto;
import com.finalproject.internMgmtSystem.dto.LoginRequest;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.repository.UserDao;
import com.finalproject.internMgmtSystem.service.AuthService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper mapper = new ObjectMapper();

    private User admin;
    private User normalUser;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        admin = new User();
        admin.setUserId(1L);
        admin.setUserName("Admin");
        admin.setEmail("admin@mail.com");
        admin.setRole("ADMIN");

        normalUser = new User();
        normalUser.setUserId(2L);
        normalUser.setUserName("User");
        normalUser.setEmail("user@mail.com");
        normalUser.setRole("USER");
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginRequest req = new LoginRequest("user@mail.com", "123");
        when(authService.login(any(LoginRequest.class))).thenReturn("TOKEN123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("TOKEN123"));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void testCreateAdmin_Success() throws Exception {
        CreateAdminDto dto = new CreateAdminDto();
        dto.setUserName("Admin");
        dto.setEmail("newadmin@mail.com");
        dto.setPassword("pass");

        when(userDao.findAll()).thenReturn(List.of(normalUser));
        when(userDao.findByEmail("newadmin@mail.com")).thenReturn(null);
        when(passwordEncoder.encode("pass")).thenReturn("ENC_PASS");

        mockMvc.perform(post("/api/auth/create-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin created successfully!"));

        verify(userDao).save(any(User.class));
    }
}
