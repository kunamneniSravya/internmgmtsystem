package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Task;
import com.finalproject.internMgmtSystem.repository.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Task> mapper = (rs, rowNum) -> {
        Task t = new Task();
        t.setId(rs.getLong("id"));
        t.setUserId(rs.getLong("user_id"));
        t.setDescription(rs.getString("description"));
        t.setTrainerId(rs.getLong("trainer_id"));
        t.setStatus(rs.getString("status"));
        t.setDeadline(rs.getDate("deadline"));
        t.setUploadFile(rs.getString("upload_file"));
        t.setCreatedAt(rs.getTimestamp("created_at"));
        return t;
    };

    @Override
    public Task save(Task task) {
        String sql = """
            INSERT INTO tasks (user_id, description, trainer_id, status, deadline, upload_file)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                task.getUserId(),
                task.getDescription(),
                task.getTrainerId(),
                task.getStatus(),
                task.getDeadline(),
                task.getUploadFile()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        task.setId(id);
        return task;
    }

    @Override
    public Task findById(Long id) {
        List<Task> list = jdbcTemplate.query(
                "SELECT * FROM tasks WHERE id = ?",
                mapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM tasks WHERE user_id = ? ORDER BY created_at DESC",
                mapper,
                userId
        );
    }

    @Override
    public List<Task> findByTrainerId(Long trainerId) {
        return jdbcTemplate.query(
                "SELECT * FROM tasks WHERE trainer_id = ? ORDER BY created_at DESC",
                mapper,
                trainerId
        );
    }

    @Override
    public void update(Task task) {
        String sql = """
            UPDATE tasks SET description=?, status=?, deadline=?, upload_file=? WHERE id=?
        """;

        jdbcTemplate.update(sql,
                task.getDescription(),
                task.getStatus(),
                task.getDeadline(),
                task.getUploadFile(),
                task.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM tasks WHERE id = ?", id);
    }
}
