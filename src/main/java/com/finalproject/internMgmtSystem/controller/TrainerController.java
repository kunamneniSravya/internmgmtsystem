package com.finalproject.internMgmtSystem.controller;

import com.finalproject.internMgmtSystem.dto.AttendanceDto;
import com.finalproject.internMgmtSystem.dto.PerformanceDto;
import com.finalproject.internMgmtSystem.dto.TaskDto;
import com.finalproject.internMgmtSystem.dto.UserBatchDto;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer")
@CrossOrigin
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/batches/{trainerId}")
    public List<InternshipBatch> getBatches(@PathVariable Long trainerId) {
        return trainerService.getBatches(trainerId);
    }

    @PostMapping("/tasks")
    public Task addTask(@Valid @RequestBody TaskDto dto) {
        return trainerService.addTask(dto);
    }

    @GetMapping("/tasks/{trainerId}")
    public List<Task> getTasksByTrainerId(@PathVariable Long trainerId) {
        return trainerService.getTasksByTrainerId(trainerId);
    }

    @PutMapping("/tasks/{taskId}")
    public String updateTask(@Valid @RequestBody TaskDto dto, @PathVariable Long taskId) {
        trainerService.updateTask(dto, taskId);
        return "Task updated successfully";
    }

    @DeleteMapping("/tasks/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        trainerService.deleteTask(taskId);
        return "Task deleted successfully";
    }

    @PostMapping("/attendance")
    public Attendance markAttendance(@Valid @RequestBody AttendanceDto dto) {
        return trainerService.markAttendance(dto);
    }

    @PostMapping("/performance")
    public Performance updatePerformance(@Valid @RequestBody PerformanceDto dto) {
        return trainerService.updatePerformance(dto);
    }

    // ⭐ FEEDBACK FIXED — UNIQUE MAPPINGS
    @GetMapping("/feedback/name/{trainerName}")
    public List<Feedback> getFeedbackByName(@PathVariable String trainerName) {
        return trainerService.viewFeedback(trainerName);
    }

    @GetMapping("/feedback/id/{trainerId}")
    public List<Feedback> getFeedbackByTrainerId(@PathVariable Long trainerId) {
        return trainerService.viewFeedbackByTrainerId(trainerId);
    }

    @GetMapping("/performance/{trainerId}")
    public List<Performance> getPerformanceByTrainerId(@PathVariable Long trainerId) {
        return trainerService.getPerformanceByTrainerId(trainerId);
    }

    @GetMapping("/UserBatch/{trainerId}")
    public List<UserBatchDto> getUsersByTrainerId(@PathVariable int trainerId) {
        return trainerService.getUsersByTrainerId(trainerId);
    }
}
