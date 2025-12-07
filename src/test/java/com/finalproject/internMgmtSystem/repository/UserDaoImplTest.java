package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.User;
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

public class UserDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDaoImpl userDao;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User(
                1L,
                "Sravya",
                "sravya@gmail.com",
                "123",
                Date.valueOf("2003-05-10"),
                "9876543210",
                "Chennai",
                "VelTech",
                "A",
                "AIML",
                "Team1",
                1L,
                Date.valueOf("2025-01-01"),
                Date.valueOf("2025-06-01"),
                2025,
                "resume.pdf",
               
                "USER",
                new Timestamp(System.currentTimeMillis())
        );
    }

    @Test
    void testSave() {
        when(jdbcTemplate.update(anyString(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(),  any()))
                .thenReturn(1);

        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Long.class)))
                .thenReturn(1L);

        User saved = userDao.save(user);

        assertNotNull(saved);
        assertEquals(1L, saved.getUserId());
    }
    @Test
    void testFindByEmail_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("sravya@gmail.com")))
                .thenReturn(Arrays.asList(user));

        User result = userDao.findByEmail("sravya@gmail.com");

        assertNotNull(result);
        assertEquals("Sravya", result.getUserName());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("notexists@gmail.com")))
                .thenReturn(Collections.emptyList());

        User result = userDao.findByEmail("notexists@gmail.com");

        assertNull(result);
    }

    @Test
    void testFindById_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(user));

        User result = userDao.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        User result = userDao.findById(1L);

        assertNull(result);
    }

    @Test
    void testFindAll() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(user, user));

        List<User> list = userDao.findAll();

        assertEquals(2, list.size());
    }

    @Test
    void testFindAll_Empty() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        List<User> list = userDao.findAll();

        assertTrue(list.isEmpty());
    }

    @Test
    void testUpdate() {
        when(jdbcTemplate.update(anyString(),
                any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(),  any()))
                .thenReturn(1);

        assertDoesNotThrow(() -> userDao.update(user));
    }

    @Test
    void testDeleteById() {
        when(jdbcTemplate.update(anyString(), eq(1L)))
                .thenReturn(1);

        assertDoesNotThrow(() -> userDao.deleteById(1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "ADMIN", "TRAINER"})
    void testParameterizedRoles(String role) {
        user.setRole(role);
        assertNotNull(user.getRole());
        assertTrue(user.getRole().length() > 2);
    }

    @Disabled("Example skipped test - UserDaoImpl")
    @Test
    void testDisabledExample() {
        fail("This test is disabled intentionally");
    }
}