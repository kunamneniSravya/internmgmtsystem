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

import com.finalproject.internMgmtSystem.model.Attendance;

public class AttendanceDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AttendanceDaoImpl attendanceDao;

    private Attendance attendance1;
    private Attendance attendance2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        attendance1 = new Attendance(1L, 10L, 100L, Date.valueOf("2025-01-01"), true);
        attendance2 = new Attendance(2L, 11L, 100L, Date.valueOf("2025-01-02"), false);
    }

    @Test
    void testSave_Success() {
        when(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class))
                .thenReturn(1L);

        Attendance saved = attendanceDao.save(attendance1);

        assertNotNull(saved);
        assertEquals(1L, saved.getAttendanceId());
    }

    // NEGATIVE: Failure to return key (null key)
    @Test
    void testSave_FailsNoKey() {
        when(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class))
                .thenReturn(null);

        Attendance saved = attendanceDao.save(attendance1);

        assertNull(saved.getAttendanceId());
    }

    @Test
    void testFindByUserIdAndDate_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10L), any(Date.class)))
                .thenReturn(Arrays.asList(attendance1));

        Attendance result = attendanceDao.findByUserIdAndDate(10L, attendance1.getDate());

        assertNotNull(result);
        assertEquals(10L, result.getUserId());
    }

    // NEGATIVE: No record found
    @Test
    void testFindByUserIdAndDate_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong(), any(Date.class)))
                .thenReturn(Collections.emptyList());

        Attendance result = attendanceDao.findByUserIdAndDate(10L, attendance1.getDate());

        assertNull(result);
    }

    @Test
    void testFindByUserId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10L)))
                .thenReturn(Arrays.asList(attendance1, attendance2));

        List<Attendance> result = attendanceDao.findByUserId(10L);

        assertEquals(2, result.size());
    }

    // NEGATIVE: Empty list
    @Test
    void testFindByUserId_Empty() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10L)))
                .thenReturn(Collections.emptyList());

        List<Attendance> result = attendanceDao.findByUserId(10L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByBatchCode_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(100L)))
                .thenReturn(Arrays.asList(attendance1));

        List<Attendance> result = attendanceDao.findByBatchCode(100L);

        assertEquals(1, result.size());
    }

    // NEGATIVE: No batches found
    @Test
    void testFindByBatchCode_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(100L)))
                .thenReturn(Collections.emptyList());

        List<Attendance> result = attendanceDao.findByBatchCode(100L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdate_Success() {
        when(jdbcTemplate.update(anyString(), anyBoolean(), anyLong()))
                .thenReturn(1);

        assertDoesNotThrow(() -> attendanceDao.update(attendance1));
    }

    // NEGATIVE: No rows updated
    @Test
    void testUpdate_NoRowsUpdated() {
        when(jdbcTemplate.update(anyString(), anyBoolean(), anyLong()))
                .thenReturn(0);

        attendanceDao.update(attendance1); // should not throw error

        verify(jdbcTemplate, times(1))
                .update(anyString(), anyBoolean(), anyLong());
    }

    @Test
    void testDelete_Success() {
        when(jdbcTemplate.update(anyString(), anyLong()))
                .thenReturn(1);

        attendanceDao.delete(1L);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L));
    }

    // NEGATIVE: No rows deleted
    @Test
    void testDelete_NoRowsDeleted() {
        when(jdbcTemplate.update(anyString(), anyLong()))
                .thenReturn(0);

        attendanceDao.delete(5L);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(5L));
    }
    
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testParameterizedAttendanceStatus(boolean present) {
        Attendance a = new Attendance();
        a.setPresent(present);
        assertNotNull(a.getPresent());
    }

    @Disabled("Disabled sample test for Attendance DAO")
    @Test
    void testDisabledAttendance() {
        fail("Should be skipped");
    }
}