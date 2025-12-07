package com.finalproject.internMgmtSystem.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.finalproject.internMgmtSystem.dto.UserBatchDto;

@Repository
public class UserBatchDaoImpl implements UserBatchDao {

    private final JdbcTemplate jdbcTemplate;

    public UserBatchDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserBatchDto> getUsersByTrainerId(int trainerId) {
        String sql = "SELECT u.user_id, u.user_name, u.email, u.contact_no, u.batch_code, " +
                     "b.batch_name, b.course_name, b.trainer_id " +
                     "FROM users u " +
                     "JOIN internship_batches b ON b.batch_code = u.batch_code " +
                     "WHERE b.trainer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UserBatchDto dto = new UserBatchDto();
            dto.setUserId(rs.getLong("user_id")); // <-- ADDED
            dto.setUserName(rs.getString("user_name"));
            dto.setEmail(rs.getString("email"));
            dto.setContactNo(rs.getString("contact_no"));
            dto.setBatchCode(rs.getString("batch_code"));
            dto.setBatchName(rs.getString("batch_name"));
            dto.setCourseName(rs.getString("course_name"));
            dto.setTrainerId(rs.getInt("trainer_id"));
            return dto;
        }, trainerId);
    }

}
