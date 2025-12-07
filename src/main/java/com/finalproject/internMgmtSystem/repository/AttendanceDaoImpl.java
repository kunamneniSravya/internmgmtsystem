package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Attendance;
import com.finalproject.internMgmtSystem.repository.AttendanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Attendance> mapper = (rs, rowNum) -> {
        Attendance a = new Attendance();
        a.setAttendanceId(rs.getLong("attendance_id"));
        a.setUserId(rs.getLong("user_id"));
        a.setBatchCode(rs.getLong("batch_code"));
        a.setDate(rs.getDate("date"));
        a.setPresent(rs.getBoolean("present"));
        return a;
    };

    @Override
    public Attendance save(Attendance attendance) {
        String sql = """
            INSERT INTO attendance (user_id, batch_code, date, present)
            VALUES (?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                attendance.getUserId(),
                attendance.getBatchCode(),
                attendance.getDate(),
                attendance.getPresent()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        attendance.setAttendanceId(id);
        return attendance;
    }

    @Override
    public Attendance findByUserIdAndDate(Long userId, Date date) {
        List<Attendance> list = jdbcTemplate.query(
                "SELECT * FROM attendance WHERE user_id = ? AND date = ?",
                mapper,
                userId,
                date
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Attendance> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM attendance WHERE user_id = ? ORDER BY date DESC",
                mapper,
                userId
        );
    }

    @Override
    public List<Attendance> findByBatchCode(Long batchCode) {
        return jdbcTemplate.query(
                "SELECT * FROM attendance WHERE batch_code = ? ORDER BY date DESC",
                mapper,
                batchCode
        );
    }

    @Override
    public void update(Attendance attendance) {
        String sql = """
            UPDATE attendance SET present = ? WHERE attendance_id = ?
        """;
        jdbcTemplate.update(sql, attendance.getPresent(), attendance.getAttendanceId());
    }

    @Override
    public void delete(Long attendanceId) {
        jdbcTemplate.update("DELETE FROM attendance WHERE attendance_id = ?", attendanceId);
    }
}
