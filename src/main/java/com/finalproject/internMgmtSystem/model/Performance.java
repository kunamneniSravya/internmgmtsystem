package com.finalproject.internMgmtSystem.model;

import java.sql.Timestamp;

public class Performance {

    private Long id;
    private Long userId;
    private Long trainerId;
    private Long batchCode;      // NEW
    private String trainerName;  // NEW
    private String remarks;
    private Integer taskEvaluationScore;
    private Timestamp createdAt;

    public Performance() {}

    public Performance(Long id, Long userId, Long trainerId, Long batchCode, String trainerName,
                       String remarks, Integer taskEvaluationScore, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.trainerId = trainerId;
        this.batchCode = batchCode;
        this.trainerName = trainerName;
        this.remarks = remarks;
        this.taskEvaluationScore = taskEvaluationScore;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }

    public Long getBatchCode() { return batchCode; }
    public void setBatchCode(Long batchCode) { this.batchCode = batchCode; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Integer getTaskEvaluationScore() { return taskEvaluationScore; }
    public void setTaskEvaluationScore(Integer taskEvaluationScore) { this.taskEvaluationScore = taskEvaluationScore; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
