package com.finalproject.internMgmtSystem.service;

import com.finalproject.internMgmtSystem.dto.FeedbackDto;
import com.finalproject.internMgmtSystem.dto.RegisterUserDto;
import com.finalproject.internMgmtSystem.exception.ResourceNotFoundException;
import com.finalproject.internMgmtSystem.exception.UserAlreadyExistsException;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private PerformanceDao performanceDao;

    @Autowired
    private StipendDao stipendDao;

    @Autowired
    private BatchDao batchDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterUserDto dto) {

        User exists = userDao.findByEmail(dto.getEmail());
        if (exists != null) {
            throw new UserAlreadyExistsException("User email already exists: " + dto.getEmail());
        }

        InternshipBatch batch = batchDao.findByBatchCode(dto.getBatchCode());
        if (batch == null) {
            throw new ResourceNotFoundException("Batch not found with code: " + dto.getBatchCode());
        }
        if (batch.getAvailableSeats() <= 0) {
            throw new ResourceNotFoundException("No available seats in batch: " + dto.getBatchCode());
        }
        batchDao.decrementAvailableSeats(dto.getBatchCode());

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setDob(dto.getDob());
        user.setContactNo(dto.getContactNo());
        user.setAddress(dto.getAddress());
        user.setCollegeName(dto.getCollegeName());
        user.setGrade(dto.getGrade());
        user.setMajor(dto.getMajor());
        user.setTeam(dto.getTeam());
        user.setBatchCode(dto.getBatchCode());
        user.setStartDate(dto.getStartDate());
        user.setEndDate(dto.getEndDate());
        user.setGraduatingYear(dto.getGraduatingYear());
        user.setResume(dto.getResume());
//        user.setProfilePic(dto.getProfilePic());
        user.setRole("USER");

        return userDao.save(user);
    }

    public List<Task> getTasks(Long userId) {
        return taskDao.findByUserId(userId); // ✨ Allow empty list
    }

    public List<Performance> getPerformance(Long userId) {
        return performanceDao.findByUserId(userId); // ✨ Allow empty list
    }

    public List<Stipend> getStipendHistory(Long userId) {
        return stipendDao.findByUserId(userId); // ✨ Allow empty list
    }

    public InternshipBatch getBatchDetails(Long batchCode) {
        InternshipBatch batch = batchDao.findById(batchCode);
        if (batch == null) {
            throw new ResourceNotFoundException("Batch not found with code: " + batchCode);
        }
        return batch;
    }

    public Feedback submitFeedback(FeedbackDto dto) {

        User user = userDao.findById(dto.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        InternshipBatch batch = batchDao.findByBatchCode(user.getBatchCode());
        if (batch == null) {
            throw new ResourceNotFoundException("Batch not found");
        }

        Feedback f = new Feedback();
        f.setUserId(user.getUserId());
        f.setUserName(user.getUserName());
        f.setBatchCode(user.getBatchCode());
        f.setTrainerId(batch.getTrainerId());
        f.setTrainerName(batch.getTrainerName());
        f.setFeedback(dto.getFeedback());
        f.setRating(dto.getRating());

        return feedbackDao.save(f);
    }



    public List<Attendance> getAttendance(Long userId) {
        return attendanceDao.findByUserId(userId); // ✨ Allow empty list
    }
}
