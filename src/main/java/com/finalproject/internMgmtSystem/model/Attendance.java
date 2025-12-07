package com.finalproject.internMgmtSystem.model;

import java.sql.Date;

public class Attendance {

    private Long attendanceId;
    private Long userId;
    private Long batchCode;
    private Date date;
    private Boolean present;

    public Attendance() {}

    public Attendance(Long attendanceId, Long userId, Long batchCode, Date date, Boolean present) {
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.batchCode = batchCode;
        this.date = date;
        this.present = present;
    }

    // Getters & Setters
    public Long getAttendanceId() { return attendanceId; }
    public void setAttendanceId(Long attendanceId) { this.attendanceId = attendanceId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBatchCode() { return batchCode; }
    public void setBatchCode(Long batchCode) { this.batchCode = batchCode; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Boolean getPresent() { return present; }
    public void setPresent(Boolean present) { this.present = present; }
}
