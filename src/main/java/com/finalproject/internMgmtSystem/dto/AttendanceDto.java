package com.finalproject.internMgmtSystem.dto;

import jakarta.validation.constraints.*;
import java.sql.Date;

public class AttendanceDto {

    

	@NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Batch code is required")
    private Long batchCode;

    @NotNull(message = "Date is required")
    private Date date;

    @NotNull(message = "Attendance status is required (true/false)")
    private Boolean present;

    public AttendanceDto() {}

    public AttendanceDto(@NotNull(message = "User ID is required") Long userId,
			@NotNull(message = "Batch code is required") Long batchCode,
			@NotNull(message = "Date is required") Date date,
			@NotNull(message = "Attendance status is required (true/false)") Boolean present) {
		super();
		this.userId = userId;
		this.batchCode = batchCode;
		this.date = date;
		this.present = present;
	}

    // Getters & Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(Long batchCode) {
        this.batchCode = batchCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
