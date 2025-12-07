package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Trainer;
import com.finalproject.internMgmtSystem.repository.TrainerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Trainer> mapper = (rs, rowNum) -> {
        Trainer t = new Trainer();
        t.setTrainerId(rs.getLong("trainer_id"));
        t.setName(rs.getString("name"));
        t.setEmail(rs.getString("email"));
        t.setPassword(rs.getString("password"));
        t.setContact(rs.getString("contact"));
        t.setExperience(rs.getString("experience"));
        t.setSkills(rs.getString("skills"));
    
        t.setBio(rs.getString("bio"));
        t.setRole(rs.getString("role"));
        t.setCreatedAt(rs.getTimestamp("created_at"));
        return t;
    };

    @Override
    public Trainer save(Trainer trainer) {
        String sql = "INSERT INTO trainers (name, email, password, contact, experience, skills,  bio, role) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                trainer.getName(),
                trainer.getEmail(),
                trainer.getPassword(),
                trainer.getContact(),
                trainer.getExperience(),
                trainer.getSkills(),
           
                trainer.getBio(),
                trainer.getRole()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        trainer.setTrainerId(id);
        return trainer;
    }

    @Override
    public Trainer findByEmail(String email) {
        List<Trainer> list = jdbcTemplate.query(
                "SELECT * FROM trainers WHERE email = ?",
                mapper,
                email
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Trainer findById(Long id) {
        List<Trainer> list = jdbcTemplate.query(
                "SELECT * FROM trainers WHERE trainer_id = ?",
                mapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Trainer> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM trainers",
                mapper
        );
    }

    @Override
    public void update(Trainer trainer) {
        String sql = "UPDATE trainers SET name=?, contact=?, experience=?, skills=?, bio=? "
                   + "WHERE trainer_id=?";

        jdbcTemplate.update(sql,
                trainer.getName(),
                trainer.getContact(),
                trainer.getExperience(),
                trainer.getSkills(),
              
                trainer.getBio(),
                trainer.getTrainerId()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM trainers WHERE trainer_id = ?",
                id
        );
    }

    @Override
    public void deleteByEmail(String email) {
        jdbcTemplate.update("DELETE FROM trainers WHERE email = ?", email);
    }
}
