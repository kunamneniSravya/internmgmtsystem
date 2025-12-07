package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Performance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PerformanceDaoImpl implements PerformanceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Performance> mapper = (rs, rowNum) -> {
        Performance p = new Performance();
        p.setId(rs.getLong("id"));
        p.setUserId(rs.getLong("user_id"));
        p.setTrainerId(rs.getLong("trainer_id"));
        p.setBatchCode(rs.getLong("batch_code"));        // NEW
        p.setTrainerName(rs.getString("trainer_name"));  // NEW
        p.setRemarks(rs.getString("remarks"));
        p.setTaskEvaluationScore(rs.getInt("task_evaluation_score"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    };

    @Override
    public Performance save(Performance p) {
        String sql = """
            INSERT INTO performance 
            (user_id, trainer_id, batch_code, trainer_name, remarks, task_evaluation_score, created_at)
            VALUES (?, ?, ?, ?, ?, ?, NOW())
        """;

        jdbcTemplate.update(sql,
                p.getUserId(),
                p.getTrainerId(),
                p.getBatchCode(),
                p.getTrainerName(),
                p.getRemarks(),
                p.getTaskEvaluationScore()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        p.setId(id);
        p.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return p;
    }

    @Override
    public List<Performance> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM performance WHERE user_id = ? ORDER BY created_at DESC",
                mapper, userId
        );
    }

    @Override
    public List<Performance> findByTrainerId(Long trainerId) {
        return jdbcTemplate.query(
                "SELECT * FROM performance WHERE trainer_id = ? ORDER BY created_at DESC",
                mapper, trainerId
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM performance WHERE id = ?", id);
    }
}
