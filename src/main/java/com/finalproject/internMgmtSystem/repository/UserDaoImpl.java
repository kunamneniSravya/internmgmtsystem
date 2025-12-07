package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> mapper = (rs, rowNum) -> {
		User u = new User();
		u.setUserId(rs.getLong("user_id"));
		u.setUserName(rs.getString("user_name"));
		u.setEmail(rs.getString("email"));
		u.setPassword(rs.getString("password"));
		u.setDob(rs.getDate("dob"));
		u.setContactNo(rs.getString("contact_no"));
		u.setAddress(rs.getString("address"));
		u.setCollegeName(rs.getString("college_name"));
		u.setGrade(rs.getString("grade"));
		u.setMajor(rs.getString("major"));
		u.setTeam(rs.getString("team"));
		u.setBatchCode(rs.getLong("batch_code"));
		u.setStartDate(rs.getDate("start_date"));
		u.setEndDate(rs.getDate("end_date"));
		u.setGraduatingYear(rs.getInt("graduating_year"));
		u.setResume(rs.getString("resume"));
//		u.setProfilePic(rs.getString("profile_pic"));
		u.setRole(rs.getString("role"));
		u.setCreatedAt(rs.getTimestamp("created_at"));
		return u;
	};

	@Override
	public User save(User user) {
		String sql = """
				INSERT INTO users
				(user_name, email, password, dob, contact_no, address, college_name, grade, major,
				team, batch_code, start_date, end_date, graduating_year, resume, role)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

		jdbcTemplate.update(sql, user.getUserName(), user.getEmail(), user.getPassword(), user.getDob(),
				user.getContactNo(), user.getAddress(), user.getCollegeName(), user.getGrade(), user.getMajor(),
				user.getTeam(), user.getBatchCode(), user.getStartDate(), user.getEndDate(), user.getGraduatingYear(),
				user.getResume(), user.getRole());

		Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		user.setUserId(id);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", mapper, email);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public User findById(Long id) {
		List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?", mapper, id);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users", mapper);
	}

	@Override
	public void update(User user) {
		String sql = """
				    UPDATE users SET user_name=?, contact_no=?, address=?, college_name=?, grade=?, major=?,
				    team=?, start_date=?, end_date=?, graduating_year=?, resume=?, profile_pic=? WHERE user_id=?
				""";

		jdbcTemplate.update(sql, user.getUserName(), user.getContactNo(), user.getAddress(), user.getCollegeName(),
				user.getGrade(), user.getMajor(), user.getTeam(), user.getBatchCode(), user.getStartDate(),
				user.getEndDate(), user.getGraduatingYear(), user.getResume(), user.getUserId());
	}

	@Override
	public void deleteById(Long id) {
		jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
	}
}
