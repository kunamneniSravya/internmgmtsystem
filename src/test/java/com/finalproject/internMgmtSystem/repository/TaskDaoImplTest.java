package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Task;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TaskDaoImpl taskDao;

    private Task task;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        task = new Task(
                1L,
                1L,
                "Complete module",
                1L,
                "PENDING",
                Date.valueOf("2025-01-10"),
                "file.pdf",
                new Timestamp(System.currentTimeMillis())
        );
    }

    @Test
    void testSave() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Long.class)))
                .thenReturn(1L);

        Task saved = taskDao.save(task);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
    }

    @Test
    void testFindById_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(task));

        Task result = taskDao.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        Task result = taskDao.findById(1L);

        assertNull(result);
    }

    @Test
    void testFindByUserId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(task, task));

        List<Task> list = taskDao.findByUserId(1L);

        assertEquals(2, list.size());
    }

    @Test
    void testFindByUserId_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        List<Task> result = taskDao.findByUserId(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByTrainerId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(task));

        List<Task> list = taskDao.findByTrainerId(1L);

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getTrainerId());
    }

    @Test
    void testFindByTrainerId_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        List<Task> list = taskDao.findByTrainerId(1L);

        assertTrue(list.isEmpty());
    }

    @Test
    void testUpdate() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        assertDoesNotThrow(() -> taskDao.update(task));
    }

    @Test
    void testDelete() {
        when(jdbcTemplate.update(anyString(), eq(1L)))
                .thenReturn(1);

        assertDoesNotThrow(() -> taskDao.delete(1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PENDING", "IN_PROGRESS", "COMPLETED"})
    void testParameterizedStatusValues(String status) {
        task.setStatus(status);

        assertNotNull(task.getStatus());
        assertTrue(task.getStatus().length() > 0);
    }

  
    @Disabled("Example skipped test - TaskDaoImpl")
    @Test
    void testDisabledExample() {
        fail("This test is disabled intentionally");
    }
}