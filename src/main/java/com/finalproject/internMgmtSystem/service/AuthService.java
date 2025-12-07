package com.finalproject.internMgmtSystem.service;

import com.finalproject.internMgmtSystem.dto.LoginRequest;
import com.finalproject.internMgmtSystem.exception.ResourceNotFoundException;
import com.finalproject.internMgmtSystem.model.Trainer;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.repository.TrainerDao;
import com.finalproject.internMgmtSystem.repository.UserDao;
import com.finalproject.internMgmtSystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest loginRequest) {

        User user = userDao.findByEmail(loginRequest.getEmail());
        Trainer trainer = trainerDao.findByEmail(loginRequest.getEmail());

        if (user == null && trainer == null) {
            throw new ResourceNotFoundException("Account not found: " + loginRequest.getEmail());
        }

        // USER LOGIN
        if (user != null) {
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            return jwtUtil.generateToken(
                    user.getUserId(),
                    user.getEmail(),
                    "ROLE_" + user.getRole()
            );
        }

        // TRAINER LOGIN
        if (!passwordEncoder.matches(loginRequest.getPassword(), trainer.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateTrainerToken(
                trainer.getTrainerId(),
                trainer.getEmail(),
                "ROLE_" + trainer.getRole(),
                trainer.getName()         // Trainer name added inside token
        );
    }
}
