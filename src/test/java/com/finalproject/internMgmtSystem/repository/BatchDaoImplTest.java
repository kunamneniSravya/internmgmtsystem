package com.finalproject.internMgmtSystem.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.finalproject.internMgmtSystem.model.InternshipBatch;

public class BatchDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BatchDaoImpl batchDao;

    private InternshipBatch batch1;
    private InternshipBatch batch2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        batch1 = new InternshipBatch(
                1L, "Batch A", "Java", 10L, "Trainer A",
                Date.valueOf("2025-01-01"), Date.valueOf("2025-03-01"),
                30, 20
        );

        batch2 = new InternshipBatch(
                2L, "Batch B", "Python", 11L, "Trainer B",
                Date.valueOf("2025-02-01"), Date.valueOf("2025-04-01"),
                40, 35
        );
    }

    @Test
    void testSave_Success() {
        when(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class))
                .thenReturn(1L);

        InternshipBatch result = batchDao.save(batch1);

        assertNotNull(result);
        assertEquals(1L, result.getBatchCode());
    }

    @Test
    void testSave_Fail_NoKey() {
        when(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class))
                .thenReturn(null);

        InternshipBatch result = batchDao.save(batch1);

        assertNull(result.getBatchCode());
    }

    @Test
    void testFindById_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(batch1));

        InternshipBatch result = batchDao.findById(1L);

        assertNotNull(result);
        assertEquals("Batch A", result.getBatchName());
    }

    @Test
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        InternshipBatch result = batchDao.findById(1L);

        assertNull(result);
    }

    @Test
    void testFindAll() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(batch1, batch2));

        List<InternshipBatch> result = batchDao.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindAll_Empty() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        List<InternshipBatch> result = batchDao.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByTrainerId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10L)))
                .thenReturn(Arrays.asList(batch1));

        List<InternshipBatch> result = batchDao.findByTrainerId(10L);

        assertEquals(1, result.size());
    }

    @Test
    void testFindByTrainerId_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10L)))
                .thenReturn(Collections.emptyList());

        List<InternshipBatch> result = batchDao.findByTrainerId(10L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByName_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("Batch A")))
                .thenReturn(Arrays.asList(batch1));

        InternshipBatch result = batchDao.findByName("Batch A");

        assertNotNull(result);
        assertEquals("Batch A", result.getBatchName());
    }

    @Test
    void testFindByName_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("Batch A")))
                .thenReturn(Collections.emptyList());

        InternshipBatch result = batchDao.findByName("Batch A");

        assertNull(result);
    }

    @Test
    void testFindByBatchCode_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(batch1));

        InternshipBatch result = batchDao.findByBatchCode(1L);

        assertNotNull(result);
        assertEquals("Batch A", result.getBatchName());
    }

    @Test
    void testFindByBatchCode_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        InternshipBatch result = batchDao.findByBatchCode(1L);

        assertNull(result);
    }

    @Test
    void testUpdate_Success() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        assertDoesNotThrow(() -> batchDao.update(batch1));
    }

    @Test
    void testUpdate_NoRowsUpdated() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(0);

        batchDao.update(batch1);

        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testDelete_Success() {
        when(jdbcTemplate.update(anyString(), anyLong()))
                .thenReturn(1);

        batchDao.delete(1L);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L));
    }

    @Test
    void testDelete_NoRowsDeleted() {
        when(jdbcTemplate.update(anyString(), anyLong()))
                .thenReturn(0);

        batchDao.delete(1L);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L));
    }

    @Test
    void testDecrementSeats_Success() {
        when(jdbcTemplate.update(anyString(), eq(1L)))
                .thenReturn(1);

        batchDao.decrementAvailableSeats(1L);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L));
    }

    @Test
    void testDecrementSeats_NoChange() {
        when(jdbcTemplate.update(anyString(), eq(1L)))
                .thenReturn(0);

        batchDao.decrementAvailableSeats(1L);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"Batch A", "Batch B", "Batch C"})
    void testParameterizedBatchNames(String name) {
        InternshipBatch batch = new InternshipBatch();
        batch.setBatchName(name);
        assertNotNull(batch.getBatchName());
        assertTrue(batch.getBatchName().startsWith("Batch"));
    }

    @Disabled("Disabled sample test for Batch DAO")
    @Test
    void testDisabledBatch() {
        fail("Should be skipped");
    }
}