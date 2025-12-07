package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Performance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerformanceDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PerformanceDaoImpl performanceDao;

    private Performance performance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        performance = new Performance();
        performance.setId(1L);
        performance.setUserId(1L);
        performance.setTrainerId(1L);
        performance.setRemarks("Good work");
        performance.setTaskEvaluationScore(90);
        performance.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testSave() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any()))
                .thenReturn(1);

        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Long.class)))
                .thenReturn(1L);

        Performance saved = performanceDao.save(performance);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
    }

    @Test
    void testFindByUserId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(performance));

        List<Performance> list = performanceDao.findByUserId(1L);

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getUserId());
    }

    @Test
    void testFindByUserId_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        List<Performance> list = performanceDao.findByUserId(1L);

        assertTrue(list.isEmpty());
    }

    @Test
    void testFindByTrainerId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(performance));

        List<Performance> list = performanceDao.findByTrainerId(1L);

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getTrainerId());
    }

    @Test
    void testFindByTrainerId_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        List<Performance> list = performanceDao.findByTrainerId(1L);

        assertTrue(list.isEmpty());
    }

    @Test
    void testDeleteById() {
        when(jdbcTemplate.update(anyString(), eq(1L)))
                .thenReturn(1);

        assertDoesNotThrow(() -> performanceDao.deleteById(1L));
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Excellent work", "Good job", "Needs improvement"})
    void testParameterizedRemarks(String remark) {

        Performance p = new Performance();
        p.setRemarks(remark);

        assertNotNull(p.getRemarks());
        assertTrue(p.getRemarks().length() > 3);
    }

    @Disabled("This is a skipped test ")
    @Test
    void testDisabledExample() {
        fail("This test is intentionally disabled");
    }
}
