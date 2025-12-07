package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackDaoImpl implements FeedbackDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Feedback> mapper = (rs, rowNum) -> {
        Feedback f = new Feedback();
        f.setId(rs.getLong("id"));
        f.setUserId(rs.getLong("user_id"));
        f.setUserName(rs.getString("user_name"));
        f.setBatchCode(rs.getLong("batch_code"));
        f.setTrainerId(rs.getLong("trainer_id")); // ⭐ ADDED
        f.setTrainerName(rs.getString("trainer_name"));
        f.setDate(rs.getTimestamp("date"));
        f.setFeedback(rs.getString("feedback"));
        f.setRating(rs.getInt("rating"));
        return f;
    };

    @Override
    public Feedback save(Feedback feedback) {
        String sql = """
            INSERT INTO feedback (user_id, user_name, batch_code, trainer_id, trainer_name, feedback, rating)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                feedback.getUserId(),
                feedback.getUserName(),
                feedback.getBatchCode(),
                feedback.getTrainerId(), // ⭐ ADDED
                feedback.getTrainerName(),
                feedback.getFeedback(),
                feedback.getRating()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        feedback.setId(id);
        return feedback;
    }

    @Override
    public List<Feedback> findByTrainerId(Long trainerId) {
        return jdbcTemplate.query(
                "SELECT * FROM feedback WHERE trainer_id = ? ORDER BY date DESC",
                mapper,
                trainerId
        );
    }

    @Override
    public List<Feedback> findByTrainerName(String trainerName) {
        return jdbcTemplate.query(
                "SELECT * FROM feedback WHERE trainer_name = ? ORDER BY date DESC",
                mapper,
                trainerName
        );
    }

    @Override
    public List<Feedback> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM feedback WHERE user_id = ? ORDER BY date DESC",
                mapper,
                userId
        );
    }

    @Override
    public List<Feedback> findAll() {
        return jdbcTemplate.query("SELECT * FROM feedback ORDER BY date DESC", mapper);
    }
}
