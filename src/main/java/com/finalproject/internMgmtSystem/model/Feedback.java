package com.finalproject.internMgmtSystem.model;

import java.sql.Timestamp;

public class Feedback {

    private Long id;
    private Long userId;
    private String userName;
    private Long batchCode;
    private Long trainerId; // ⭐ ADDED
    private String trainerName;
    private Timestamp date;
    private String feedback;
    private Integer rating;

    public Feedback() {}

    public Feedback(Long id, Long userId, String userName, Long batchCode,
                    Long trainerId, String trainerName,
                    Timestamp date, String feedback, Integer rating) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.batchCode = batchCode;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.date = date;
        this.feedback = feedback;
        this.rating = rating;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getBatchCode() { return batchCode; }
    public void setBatchCode(Long batchCode) { this.batchCode = batchCode; }

    public Long getTrainerId() { return trainerId; } // ⭐ ADDED
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; } // ⭐ ADDED

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}
