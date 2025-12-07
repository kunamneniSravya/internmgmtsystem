package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Trainer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TrainerDaoImpl trainerDao;

    private Trainer trainer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        trainer = new Trainer(
                1L,
                "Sravya",
                "sravya@gmail.com",
                "123",
                "9876543210",
                "3",
                "Java, Spring",
                
                "software developer",
                "TRAINER",
                new Timestamp(System.currentTimeMillis())
        );
    }

    @Test
    void testSave() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Long.class)))
                .thenReturn(1L);

        Trainer saved = trainerDao.save(trainer);

        assertNotNull(saved);
        assertEquals(1L, saved.getTrainerId());
    }

    @Test
    void testFindByEmail_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("sravya@gmail.com")))
                .thenReturn(Arrays.asList(trainer));

        Trainer result = trainerDao.findByEmail("sravya@gmail.com");

        assertNotNull(result);
        assertEquals("Sravya", result.getName());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("unknown@gmail.com")))
                .thenReturn(Collections.emptyList());

        Trainer result = trainerDao.findByEmail("unknown@gmail.com");

        assertNull(result);
    }

    @Test
    void testFindById_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Arrays.asList(trainer));

        Trainer result = trainerDao.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTrainerId());
    }

    @Test
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(Collections.emptyList());

        Trainer result = trainerDao.findById(1L);

        assertNull(result);
    }

    @Test
    void testFindAll() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(trainer, trainer));

        List<Trainer> list = trainerDao.findAll();

        assertEquals(2, list.size());
    }

    @Test
    void testFindAll_Empty() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        List<Trainer> list = trainerDao.findAll();

        assertTrue(list.isEmpty());
    }

    @Test
    void testUpdate() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);

        assertDoesNotThrow(() -> trainerDao.update(trainer));
    }

    @Test
    void testDeleteById() {
        when(jdbcTemplate.update(anyString(), eq(1L)))
                .thenReturn(1);

        assertDoesNotThrow(() -> trainerDao.deleteById(1L));
    }

    @Test
    void testDeleteByEmail() {
        when(jdbcTemplate.update(anyString(), eq("sravya@gmail.com")))
                .thenReturn(1);

        assertDoesNotThrow(() -> trainerDao.deleteByEmail("sravya@gmail.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Java", "Python", "DevOps"})
    void testParameterizedSkills(String skill) {
        trainer.setSkills(skill);

        assertNotNull(trainer.getSkills());
        assertTrue(trainer.getSkills().length() > 0);
    }

    @Disabled("Example skipped test - TrainerDaoImpl")
    @Test
    void testDisabledExample() {
        fail("This test is disabled intentionally");
    }
}