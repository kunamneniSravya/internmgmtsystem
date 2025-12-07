package com.finalproject.internMgmtSystem.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.finalproject.internMgmtSystem.model.Feedback;

public class FeedbackDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private FeedbackDaoImpl feedbackDao;

    private Feedback f1;
    private Feedback f2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        f1 = new Feedback();
        f1.setId(1L);
        f1.setUserId(10L);
        f1.setUserName("UserA");
        f1.setBatchCode(100L);
        f1.setTrainerId(20L);
        f1.setTrainerName("TrainerA");
        f1.setDate(Timestamp.valueOf("2025-01-01 00:00:00"));
        f1.setFeedback("Good");
        f1.setRating(5);

        f2 = new Feedback();
        f2.setId(2L);
        f2.setUserId(11L);
        f2.setUserName("UserB");
        f2.setBatchCode(100L);
        f2.setTrainerId(20L);
        f2.setTrainerName("TrainerA");
        f2.setDate(Timestamp.valueOf("2025-01-02 00:00:12"));
        f2.setFeedback("Average");
        f2.setRating(3);
    }

    @Test
    void testSave() {
        when(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class)).thenReturn(1L);
        Feedback result = feedbackDao.save(f1);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByTrainerName_Positive() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("TrainerA")))
                .thenReturn(Arrays.asList(f1, f2));
        List<Feedback> list = feedbackDao.findByTrainerName("TrainerA");
        assertEquals(2, list.size());
    }

    @Test
    void testFindByTrainerName_Negative() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("XYZ")))
                .thenReturn(Arrays.asList());
        List<Feedback> list = feedbackDao.findByTrainerName("XYZ");
        assertTrue(list.isEmpty());
    }

    @Test
    void testFindByTrainerId_Positive() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(20L)))
                .thenReturn(Arrays.asList(f1, f2));
        List<Feedback> list = feedbackDao.findByTrainerId(20L);
        assertEquals(2, list.size());
    }

    @Test
    void testFindByTrainerId_Negative() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(999L)))
                .thenReturn(Arrays.asList());
        List<Feedback> list = feedbackDao.findByTrainerId(999L);
        assertTrue(list.isEmpty());
    }

    @Test
    void testFindByUserId_Positive() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10L)))
                .thenReturn(Arrays.asList(f1));
        List<Feedback> list = feedbackDao.findByUserId(10L);
        assertEquals(1, list.size());
    }

    @Test
    void testFindByUserId_Negative() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(999L)))
                .thenReturn(Arrays.asList());
        List<Feedback> list = feedbackDao.findByUserId(999L);
        assertTrue(list.isEmpty());
    }

    @Test
    void testFindAll_Positive() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(f1, f2));
        List<Feedback> list = feedbackDao.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void testFindAll_Negative() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList());
        List<Feedback> list = feedbackDao.findAll();
        assertTrue(list.isEmpty());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"Good", "Average", "Excellent"})
    void testParameterizedFeedbackMessages(String msg) {
        Feedback f = new Feedback();
        f.setFeedback(msg);
        assertNotNull(f.getFeedback());
        assertTrue(f.getFeedback().length() > 0);
    }

    @Disabled("Disabled sample test for Feedback DAO")
    @Test
    void testDisabledFeedback() {
        fail("Should be skipped");
    }
}