package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;
import java.sql.Date;

public class TaskDto {

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotBlank(message = "Task description is required")
	private String description;

	@NotNull(message = "Trainer ID is required")
	private Long trainerId;

	@NotBlank(message = "Status is required")
	private String status;

	@NotNull(message = "Deadline is required")
	private Date deadline;

	private String uploadFile;

	public TaskDto() {
	}

	public TaskDto(@NotNull(message = "User ID is required") Long userId,
			@NotBlank(message = "Task description is required") String description,
			@NotNull(message = "Trainer ID is required") Long trainerId,
			@NotBlank(message = "Status is required") String status,
			@NotNull(message = "Deadline is required") Date deadline, String uploadFile) {
		super();
		this.userId = userId;
		this.description = description;
		this.trainerId = trainerId;
		this.status = status;
		this.deadline = deadline;
		this.uploadFile = uploadFile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	// getters & setters
}
