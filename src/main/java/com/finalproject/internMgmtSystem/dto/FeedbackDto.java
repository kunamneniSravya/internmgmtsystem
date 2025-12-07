package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;

public class FeedbackDto {

//    @NotNull(message = "User ID is required")
    private Long userId;

//    @NotBlank(message = "User name is required")
    private String userName;

//    @NotNull(message = "Batch code is required")
    private Long batchCode;

//    @NotNull(message = "Trainer ID is required") // ⭐ NEW
    private Long trainerId;

//    @NotBlank(message = "Trainer name is required")
    private String trainerName;

    @NotBlank(message = "Feedback cannot be empty")
    private String feedback;

    @NotNull(message = "Rating is required")
    @Min(value = 1)
    @Max(value = 5)
    private Integer rating;

    public FeedbackDto() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getBatchCode() { return batchCode; }
    public void setBatchCode(Long batchCode) { this.batchCode = batchCode; }

    public Long getTrainerId() { return trainerId; } // ⭐ getter added
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; } // ⭐ setter added

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}
