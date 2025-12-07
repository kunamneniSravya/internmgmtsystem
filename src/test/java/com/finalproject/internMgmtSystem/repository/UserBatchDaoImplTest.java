package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.dto.UserBatchDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserBatchDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserBatchDaoImpl userBatchDao;

    private UserBatchDto dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        dto = new UserBatchDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setEmail("sravya@gmail.com");
        dto.setContactNo("9876543210");
        dto.setBatchCode("1");
        dto.setBatchName("Batch A");
        dto.setCourseName("Java Fullstack");
        dto.setTrainerId(1);
    }

    @Test
    void testGetUsersByTrainerId_Found() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1)))
                .thenReturn(Arrays.asList(dto, dto));

        List<UserBatchDto> list = userBatchDao.getUsersByTrainerId(1);

        assertEquals(2, list.size());
        assertEquals("Sravya", list.get(0).getUserName());
        assertEquals(1, list.get(0).getTrainerId());
    }

    @Test
    void testGetUsersByTrainerId_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1)))
                .thenReturn(Collections.emptyList());

        List<UserBatchDto> list = userBatchDao.getUsersByTrainerId(1);

        assertTrue(list.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testParameterizedTrainerIds(int trainerId) {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(trainerId)))
                .thenReturn(Collections.emptyList());

        List<UserBatchDto> list = userBatchDao.getUsersByTrainerId(trainerId);

        assertNotNull(list);
    }

    @Disabled("Example skipped test - UserBatchDaoImpl")
    @Test
    void testDisabledExample() {
        fail("This test is disabled intentionally");
    }
}