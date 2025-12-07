package com.finalproject.internMgmtSystem.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Task {

    private Long id;
    private Long userId;
    private String description;
    private Long trainerId;
    private String status;
    private Date deadline;
    private String uploadFile;
    private Timestamp createdAt;

    public Task() {}

    public Task(Long id, Long userId, String description, Long trainerId, String status, Date deadline,
                String uploadFile, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.trainerId = trainerId;
        this.status = status;
        this.deadline = deadline;
        this.uploadFile = uploadFile;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public String getUploadFile() { return uploadFile; }
    public void setUploadFile(String uploadFile) { this.uploadFile = uploadFile; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
