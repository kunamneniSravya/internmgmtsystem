package com.finalproject.internMgmtSystem.controller;

import com.finalproject.internMgmtSystem.dto.FeedbackDto;
import com.finalproject.internMgmtSystem.dto.RegisterUserDto;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.repository.BatchDao;
import com.finalproject.internMgmtSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BatchDao batchDao; // 🔥 FIXED — Autowired properly

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterUserDto dto) {
        return userService.registerUser(dto);
    }

    @GetMapping("/{userId}/tasks")
    public List<Task> getTasks(@PathVariable Long userId) {
        return userService.getTasks(userId);
    }

    @GetMapping("/{userId}/performance")
    public List<Performance> getPerformance(@PathVariable Long userId) {
        return userService.getPerformance(userId);
    }

    @GetMapping("/{userId}/stipends")
    public List<Stipend> getStipends(@PathVariable Long userId) {
        return userService.getStipendHistory(userId);
    }

    @GetMapping("/batch/{batchCode}")
    public InternshipBatch getBatchDetails(@PathVariable Long batchCode) {
        return userService.getBatchDetails(batchCode);
    }

    @PostMapping("/feedback")
    public Feedback submitFeedback(@Valid @RequestBody FeedbackDto dto) {
        return userService.submitFeedback(dto);
    }

    @GetMapping("/{userId}/attendance")
    public List<Attendance> getAttendance(@PathVariable Long userId) {
        return userService.getAttendance(userId);
    }

    // 📌 NEW PUBLIC ENDPOINT
    @GetMapping("/batches")
    public List<InternshipBatch> getAllBatchesPublic() {
        return batchDao.findAll(); // 🟢 Now resolved and working!
    }
}
