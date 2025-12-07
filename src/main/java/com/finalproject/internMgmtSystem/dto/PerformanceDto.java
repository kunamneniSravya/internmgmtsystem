package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;

public class PerformanceDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Trainer ID is required")
    private Long trainerId;

    @NotNull(message = "Batch Code is required") // NEW
    private Long batchCode;

    @NotBlank(message = "Trainer name is required") // NEW
    private String trainerName;

    @NotBlank(message = "Remarks cannot be empty")
    private String remarks;

    @NotNull(message = "Task score is required")
    @Min(value = 0, message = "Score must be >= 0")
    @Max(value = 100, message = "Score must be <= 100")
    private Integer taskEvaluationScore;

    public PerformanceDto() {}

    public PerformanceDto(Long userId, Long trainerId, Long batchCode, String trainerName,
                          String remarks, Integer taskEvaluationScore) {
        this.userId = userId;
        this.trainerId = trainerId;
        this.batchCode = batchCode;
        this.trainerName = trainerName;
        this.remarks = remarks;
        this.taskEvaluationScore = taskEvaluationScore;
    }

    // Getters & Setters
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
}
