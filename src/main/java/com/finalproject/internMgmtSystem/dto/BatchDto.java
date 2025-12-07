package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;
import java.sql.Date;

public class BatchDto {

	@NotBlank(message = "Batch name is required")
	private String batchName;

	@NotBlank(message = "Course name is required")
	private String courseName;

	@NotNull(message = "Trainer ID is required")
	@Min(value = 1, message = "Trainer ID must be > 0")
	private Long trainerId;

	@NotNull(message = "Start date is required")
	private Date startDate;

	@NotNull(message = "End date is required")
	private Date endDate;

	@NotNull(message = "Total seats are required")
	@Min(value = 1, message = "Total seats must be >= 1")
	private Integer totalSeats;

	@NotNull(message = "Available seats are required")
	@Min(value = 0, message = "Available seats cannot be negative")
	private Integer availableSeats;

	public BatchDto() {
	}

	public BatchDto(@NotBlank(message = "Batch name is required") String batchName,
			@NotBlank(message = "Course name is required") String courseName,
			@NotNull(message = "Trainer ID is required") @Min(value = 1, message = "Trainer ID must be > 0") Long trainerId,
			@NotNull(message = "Start date is required") Date startDate,
			@NotNull(message = "End date is required") Date endDate,
			@NotNull(message = "Total seats are required") @Min(value = 1, message = "Total seats must be >= 1") Integer totalSeats,
			@NotNull(message = "Available seats are required") @Min(value = 0, message = "Available seats cannot be negative") Integer availableSeats) {
		super();
		this.batchName = batchName;
		this.courseName = courseName;
		this.trainerId = trainerId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
	}

	// getters & setters
	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
}
