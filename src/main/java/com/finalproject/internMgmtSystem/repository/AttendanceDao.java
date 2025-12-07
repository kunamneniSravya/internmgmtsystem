package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Attendance;
import java.sql.Date;
import java.util.List;

public interface AttendanceDao {

    Attendance save(Attendance attendance);

    Attendance findByUserIdAndDate(Long userId, Date date);

    List<Attendance> findByUserId(Long userId);

    List<Attendance> findByBatchCode(Long batchCode);

    void update(Attendance attendance);

    void delete(Long attendanceId);
    
    
}
