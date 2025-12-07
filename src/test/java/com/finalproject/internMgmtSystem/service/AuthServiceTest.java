package com.finalproject.internMgmtSystem.service;

import com.finalproject.internMgmtSystem.dto.LoginRequest;
import com.finalproject.internMgmtSystem.exception.ResourceNotFoundException;
import com.finalproject.internMgmtSystem.model.Trainer;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.repository.TrainerDao;
import com.finalproject.internMgmtSystem.repository.UserDao;
import com.finalproject.internMgmtSystem.security.JwtUtil;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock private UserDao userDao;
    @Mock private TrainerDao trainerDao;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User user;
    private Trainer trainer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setEmail("user@mail.com");
        user.setPassword("ENC");
        user.setRole("USER");

        trainer = new Trainer();
        trainer.setTrainerId(10L);
        trainer.setEmail("trainer@mail.com");
        trainer.setPassword("ENC");
        trainer.setRole("TRAINER");
        trainer.setName("Trainer A");
    }

    @Test
    void testLogin_UserSuccess() {
        LoginRequest login = new LoginRequest("user@mail.com", "123");

        when(userDao.findByEmail("user@mail.com")).thenReturn(user);
        when(passwordEncoder.matches("123", "ENC")).thenReturn(true);
        when(jwtUtil.generateToken(1L, "user@mail.com", "ROLE_USER")).thenReturn("TOKEN");

        String token = authService.login(login);

        assertEquals("TOKEN", token);
        verify(userDao).findByEmail("user@mail.com");
        verify(passwordEncoder).matches("123", "ENC");
        verify(jwtUtil).generateToken(1L, "user@mail.com", "ROLE_USER");
    }

    @Test
    void testLogin_TrainerSuccess() {
        LoginRequest login = new LoginRequest("trainer@mail.com", "123");

        when(userDao.findByEmail("trainer@mail.com")).thenReturn(null);
        when(trainerDao.findByEmail("trainer@mail.com")).thenReturn(trainer);
        when(passwordEncoder.matches("123", "ENC")).thenReturn(true);
        when(jwtUtil.generateTrainerToken(10L, "trainer@mail.com", "ROLE_TRAINER", "Trainer A"))
                .thenReturn("trainerTOKEN");

        String token = authService.login(login);

        assertEquals("trainerTOKEN", token);
        verify(trainerDao).findByEmail("trainer@mail.com");
        verify(passwordEncoder).matches("123", "ENC");
        verify(jwtUtil).generateTrainerToken(10L, "trainer@mail.com", "ROLE_TRAINER", "Trainer A");
    }

    @Test
    void testLogin_AccountNotFound() {
        LoginRequest login = new LoginRequest("none@mail.com", "123");

        when(userDao.findByEmail("none@mail.com")).thenReturn(null);
        when(trainerDao.findByEmail("none@mail.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> authService.login(login));

        verify(userDao).findByEmail("none@mail.com");
        verify(trainerDao).findByEmail("none@mail.com");
    }

    @Test
    void testLogin_UserWrongPassword() {
        LoginRequest login = new LoginRequest("user@mail.com", "wrong");

        when(userDao.findByEmail("user@mail.com")).thenReturn(user);
        when(passwordEncoder.matches("wrong", "ENC")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(login));

        verify(passwordEncoder).matches("wrong", "ENC");
    }

    @Test
    void testLogin_TrainerWrongPassword() {
        LoginRequest login = new LoginRequest("trainer@mail.com", "wrong");

        when(userDao.findByEmail("trainer@mail.com")).thenReturn(null);
        when(trainerDao.findByEmail("trainer@mail.com")).thenReturn(trainer);
        when(passwordEncoder.matches("wrong", "ENC")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(login));

        verify(passwordEncoder).matches("wrong", "ENC");
    }

    @ParameterizedTest
    @ValueSource(strings = {"user@mail.com", "trainer@mail.com"})
    void testParameterizedEmails(String email) {
        assertTrue(email.contains("@"));
    }

    @Disabled("skip")
    @Test
    void disabledTest() {
        fail("disabled");
    }
}