package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Stipend;
import com.finalproject.internMgmtSystem.repository.StipendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class StipendDaoImpl implements StipendDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Stipend> mapper = (rs, rowNum) -> {
        Stipend s = new Stipend();
        s.setId(rs.getLong("id"));
        s.setUserId(rs.getLong("user_id"));
        s.setUserName(rs.getString("user_name"));
        s.setPaymentDate(rs.getTimestamp("payment_date"));
        s.setPaymentMode(rs.getString("payment_mode"));
        s.setAmount(rs.getDouble("amount"));
        return s;
    };

    @Override
    public Stipend save(Stipend stipend) {
        String sql = """
            INSERT INTO stipend (user_id, user_name, payment_mode, amount, payment_date)
            VALUES (?, ?, ?, ?, NOW())
        """;

        jdbcTemplate.update(sql,
                stipend.getUserId(),
                stipend.getUserName(),
                stipend.getPaymentMode(),
                stipend.getAmount()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        stipend.setId(id);
        stipend.setPaymentDate(new Timestamp(System.currentTimeMillis())); // Set date in model
        
        return stipend;
    }


    @Override
    public List<Stipend> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM stipend WHERE user_id = ? ORDER BY payment_date DESC",
                mapper,
                userId
        );
    }

    @Override
    public List<Stipend> findAll() {
        return jdbcTemplate.query("SELECT * FROM stipend ORDER BY payment_date DESC", mapper);
    }
}
