package com.finalproject.internMgmtSystem.controller;

import com.finalproject.internMgmtSystem.dto.RegisterUserDto;
import com.finalproject.internMgmtSystem.dto.CreateAdminDto;
import com.finalproject.internMgmtSystem.dto.LoginRequest;
import com.finalproject.internMgmtSystem.exception.UserAlreadyExistsException;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.repository.UserDao;
import com.finalproject.internMgmtSystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;              // instance DAO

    @Autowired
    private PasswordEncoder passwordEncoder; // injected password encoder

    // LOGIN (existing)
    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return Map.of("token", token);
    }


    /**
     * Temporary endpoint to create the one-time ADMIN via Postman.
     * - Ensures only ONE admin exists in the system.
     * - After successful admin creation you should REMOVE this endpoint
     *   (or at least lock it) from SecurityConfig.
     */
    
    @PostMapping("/create-admin")
    public String createAdmin(@Valid @RequestBody CreateAdminDto dto) {

        // 1) Check if an admin already exists
        List<User> allUsers = userDao.findAll();
        boolean adminExists = allUsers.stream()
                .anyMatch(u -> "ADMIN".equalsIgnoreCase(u.getRole()));

        if (adminExists) {
            throw new UserAlreadyExistsException("Admin already exists!");
        }

        // 2) Check if email already used
        User existing = userDao.findByEmail(dto.getEmail());
        if (existing != null) {
            throw new UserAlreadyExistsException("Email already registered!");
        }

        // 3) Create admin
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ADMIN");

        userDao.save(user);

        return "Admin created successfully!";
    }

}
