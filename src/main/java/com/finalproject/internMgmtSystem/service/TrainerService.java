package com.finalproject.internMgmtSystem.service;

import com.finalproject.internMgmtSystem.dto.AttendanceDto;
import com.finalproject.internMgmtSystem.dto.PerformanceDto;
import com.finalproject.internMgmtSystem.dto.TaskDto;
import com.finalproject.internMgmtSystem.dto.UserBatchDto;
import com.finalproject.internMgmtSystem.exception.ResourceNotFoundException;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private BatchDao batchDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private PerformanceDao performanceDao;

    @Autowired
    private AttendanceDao attendanceDao;
    
    @Autowired
    private UserBatchDao UserBatchDao;

    public List<InternshipBatch> getBatches(Long trainerId) {
        List<InternshipBatch> list = batchDao.findByTrainerId(trainerId);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No batches assigned to trainer ID: " + trainerId);
        }

        return list;
    }

    public Task addTask(TaskDto dto) {

        // Optional: validate if user exists / trainer exists

        Task t = new Task();
        t.setUserId(dto.getUserId());
        t.setDescription(dto.getDescription());
        t.setTrainerId(dto.getTrainerId());
        t.setStatus(dto.getStatus());
        t.setDeadline(dto.getDeadline());
        t.setUploadFile(dto.getUploadFile());

        return taskDao.save(t);
    }

    public void updateTask(TaskDto dto, Long taskId) {
        Task t = taskDao.findById(taskId);
        if (t == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + taskId);
        }

        t.setDescription(dto.getDescription());
        t.setStatus(dto.getStatus());
        t.setDeadline(dto.getDeadline());
        t.setUploadFile(dto.getUploadFile());

        taskDao.update(t);
    }

    public void deleteTask(Long id) {
        Task exists = taskDao.findById(id);
        if (exists == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }

        taskDao.delete(id);
    }

    public Attendance markAttendance(AttendanceDto dto) {

        if (dto.getDate().toLocalDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Cannot mark attendance for future dates");
        }

        Attendance a = new Attendance();
        a.setUserId(dto.getUserId());
        a.setBatchCode(dto.getBatchCode());
        a.setDate(dto.getDate());
        a.setPresent(dto.getPresent());

        return attendanceDao.save(a);
    }

    public Performance updatePerformance(PerformanceDto dto) {

        Performance p = new Performance();

        p.setUserId(dto.getUserId());
        p.setTrainerId(dto.getTrainerId());
        p.setBatchCode(dto.getBatchCode());       // NEW
        p.setTrainerName(dto.getTrainerName());   // NEW
        p.setRemarks(dto.getRemarks());
        p.setTaskEvaluationScore(dto.getTaskEvaluationScore());

        return performanceDao.save(p);
    }


    public List<Feedback> viewFeedback(String trainerName) {
        List<Feedback> list = feedbackDao.findByTrainerName(trainerName);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No feedback found for trainer: " + trainerName);
        }

        return list;
    }
    
    public List<UserBatchDto> getUsersByTrainerId(int trainerId) {
        return UserBatchDao.getUsersByTrainerId(trainerId);
    }
    public List<Task> getTasksByTrainerId(Long trainerId) {
        List<Task> tasks = taskDao.findByTrainerId(trainerId);
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("No tasks found for trainer ID: " + trainerId);
        }
        return tasks;
    }

    public List<Performance> getPerformanceByTrainerId(Long trainerId) {
        List<Performance> list = performanceDao.findByTrainerId(trainerId);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No performance records found for trainer ID: " + trainerId);
        }
        return list;
    }
    public List<Feedback> viewFeedbackByTrainerId(Long trainerId) {
        List<Feedback> list = feedbackDao.findByTrainerId(trainerId);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No feedback found for trainer ID: " + trainerId);
        }
        return list;
    }



}
