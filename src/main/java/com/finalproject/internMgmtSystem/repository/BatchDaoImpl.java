package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.InternshipBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BatchDaoImpl implements BatchDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Updated RowMapper to include trainerName
    private RowMapper<InternshipBatch> mapper = (rs, rowNum) -> {
        InternshipBatch b = new InternshipBatch();
        b.setBatchCode(rs.getLong("batch_code"));
        b.setBatchName(rs.getString("batch_name"));
        b.setCourseName(rs.getString("course_name"));
        b.setTrainerId(rs.getLong("trainer_id"));
        b.setTrainerName(rs.getString("trainer_name")); // NEW 👌
        b.setStartDate(rs.getDate("start_date"));
        b.setEndDate(rs.getDate("end_date"));
        b.setTotalSeats(rs.getInt("total_seats"));
        b.setAvailableSeats(rs.getInt("available_seats"));
        return b;
    };

    @Override
    public InternshipBatch save(InternshipBatch batch) {
        String sql = """
                INSERT INTO internship_batches
                (batch_name, course_name, trainer_id, start_date, end_date, total_seats, available_seats)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql, batch.getBatchName(), batch.getCourseName(), batch.getTrainerId(),
                batch.getStartDate(), batch.getEndDate(), batch.getTotalSeats(), batch.getAvailableSeats());

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        batch.setBatchCode(id);
        return batch;
    }

    @Override
    public InternshipBatch findById(Long batchCode) {
        return findByBatchCode(batchCode);
    }

    @Override
    public List<InternshipBatch> findAll() {
        String sql = """
                SELECT b.*, t.name AS trainer_name
                FROM internship_batches b
                JOIN trainers t ON b.trainer_id = t.trainer_id
                """;
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<InternshipBatch> findByTrainerId(Long trainerId) {
        String sql = """
                SELECT b.*, t.name AS trainer_name
                FROM internship_batches b
                JOIN trainers t ON b.trainer_id = t.trainer_id
                WHERE b.trainer_id = ?
                """;
        return jdbcTemplate.query(sql, mapper, trainerId);
    }

    @Override
    public void update(InternshipBatch batch) {
        String sql = """
                UPDATE internship_batches SET batch_name=?, course_name=?, trainer_id=?, start_date=?,
                end_date=?, total_seats=?, available_seats=? WHERE batch_code=?
                """;
        jdbcTemplate.update(sql, batch.getBatchName(), batch.getCourseName(), batch.getTrainerId(),
                batch.getStartDate(), batch.getEndDate(), batch.getTotalSeats(), batch.getAvailableSeats(),
                batch.getBatchCode());
    }

    @Override
    public void delete(Long batchCode) {
        jdbcTemplate.update("DELETE FROM internship_batches WHERE batch_code = ?", batchCode);
    }

    @Override
    public InternshipBatch findByName(String batchName) {
        String sql = """
                SELECT b.*, t.name AS trainer_name
                FROM internship_batches b
                JOIN trainers t ON b.trainer_id = t.trainer_id
                WHERE b.batch_name = ?
                """;
        List<InternshipBatch> list = jdbcTemplate.query(sql, mapper, batchName);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public InternshipBatch findByBatchCode(Long batchCode) {
        String sql = """
                SELECT b.*, t.name AS trainer_name
                FROM internship_batches b
                JOIN trainers t ON b.trainer_id = t.trainer_id
                WHERE b.batch_code = ?
                """;
        List<InternshipBatch> list = jdbcTemplate.query(sql, mapper, batchCode);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void decrementAvailableSeats(Long batchCode) {
        String sql = """
                UPDATE internship_batches 
                SET available_seats = available_seats - 1 
                WHERE batch_code = ? AND available_seats > 0
                """;
        jdbcTemplate.update(sql, batchCode);
    }
}
