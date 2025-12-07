package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Stipend;
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

class StipendDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private StipendDaoImpl stipendDao;

    private Stipend stipend;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        stipend = new Stipend();
        stipend.setId(1L);
        stipend.setUserId(1L);
        stipend.setUserName("Sravya");
        stipend.setPaymentMode("UPI");
        stipend.setAmount(5000.0);
        stipend.setPaymentDate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testSave() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any()))
                .thenReturn(1);

        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Long.class)))
                .thenReturn(1L);

        Stipend saved = stipendDao.save(stipend);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertEquals("Sravya", saved.getUserName());
        verify(jdbcTemplate, times(1))
                .update(anyString(), any(), any(), any(), any());
    }

    @Test
    void testFindByUserId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(stipend));

        List<Stipend> list = stipendDao.findByUserId(1L);

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getUserId());
    }

    @Test
    void testFindByUserId_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        List<Stipend> list = stipendDao.findByUserId(1L);

        assertTrue(list.isEmpty());
    }

    @Test
    void testFindAll() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(stipend, stipend));

        List<Stipend> list = stipendDao.findAll();

        assertEquals(2, list.size());
    }

    @Test
    void testFindAll_Empty() {
    	when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
            	.thenReturn(Collections.emptyList());

    	List<Stipend> list = stipendDao.findAll();

    	assertNotNull(list);
    	assertTrue(list.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1000.0, 2500.5, 5000.0})
    void testParameterizedAmounts(double amount) {

        Stipend s = new Stipend();
        s.setAmount(amount);

        assertNotNull(s.getAmount());
        assertTrue(s.getAmount() > 0);
    }

    @Disabled("This is a skipped test")
    @Test
    void testDisabledExample() {
        fail("this test is disabled");
    }
}
