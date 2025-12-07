package com.finalproject.internMgmtSystem.model;

import java.sql.Date;

public class InternshipBatch {

    private Long batchCode;
    private String batchName;
    private String courseName;
    private Long trainerId;
    private String trainerName; // NEW 👌
    private Date startDate;
    private Date endDate;
    private Integer totalSeats;
    private Integer availableSeats;

    public InternshipBatch() {}

    public InternshipBatch(Long batchCode, String batchName, String courseName,
                           Long trainerId, String trainerName,
                           Date startDate, Date endDate,
                           Integer totalSeats, Integer availableSeats) {

        this.batchCode = batchCode;
        this.batchName = batchName;
        this.courseName = courseName;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    // Getters & Setters
    public Long getBatchCode() { return batchCode; }
    public void setBatchCode(Long batchCode) { this.batchCode = batchCode; }

    public String getBatchName() { return batchName; }
    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }

    public String getTrainerName() { return trainerName; } // NEW
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; } // NEW

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
}
