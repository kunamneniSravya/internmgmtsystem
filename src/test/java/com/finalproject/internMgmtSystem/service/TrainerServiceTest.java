package com.finalproject.internMgmtSystem.service;

import com.finalproject.internMgmtSystem.dto.AttendanceDto;
import com.finalproject.internMgmtSystem.dto.PerformanceDto;
import com.finalproject.internMgmtSystem.dto.TaskDto;
import com.finalproject.internMgmtSystem.dto.UserBatchDto;
import com.finalproject.internMgmtSystem.exception.ResourceNotFoundException;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Mock private BatchDao batchDao;
    @Mock private TaskDao taskDao;
    @Mock private FeedbackDao feedbackDao;
    @Mock private PerformanceDao performanceDao;
    @Mock private AttendanceDao attendanceDao;
    @Mock private UserBatchDao userBatchDao;

    @InjectMocks
    private TrainerService trainerService;

    private InternshipBatch batch;
    private Task task;
    private Attendance attendance;
    private Performance performance;
    private Feedback feedback;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        batch = new InternshipBatch();
        batch.setBatchCode(1L);
        batch.setTrainerId(1L);

        task = new Task();
        task.setId(1L);
        task.setUserId(1L);
        task.setTrainerId(1L);

        attendance = new Attendance();
        attendance.setAttendanceId(1L);
        attendance.setUserId(1L);
        attendance.setBatchCode(1L);
        attendance.setDate(Date.valueOf(LocalDate.now()));
        attendance.setPresent(true);

        performance = new Performance();
        performance.setId(1L);
        performance.setUserId(1L);
        performance.setTrainerId(1L);

        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setTrainerName("Trainer A");
        feedback.setUserId(1L);
    }

    @Test
    void testGetBatches_Success() {
        when(batchDao.findByTrainerId(1L)).thenReturn(List.of(batch));
        List<InternshipBatch> list = trainerService.getBatches(1L);
        assertEquals(1, list.size());
        verify(batchDao).findByTrainerId(1L);
    }

    @Test
    void testGetBatches_NotFound() {
        when(batchDao.findByTrainerId(1L)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> trainerService.getBatches(1L));
        verify(batchDao).findByTrainerId(1L);
    }

    @Test
    void testAddTask_Success() {
        TaskDto dto = new TaskDto(1L,"Complete Assignment",1L,"PENDING",Date.valueOf("2025-01-10"),"file.pdf");
        when(taskDao.save(any())).thenReturn(task);
        Task saved = trainerService.addTask(dto);
        assertNotNull(saved);
        verify(taskDao).save(any());
    }

    @Test
    void testUpdateTask_Success() {
        TaskDto dto = new TaskDto(1L,"Updated",1L,"DONE",Date.valueOf("2025-01-15"),"file.pdf");
        when(taskDao.findById(1L)).thenReturn(task);
        assertDoesNotThrow(() -> trainerService.updateTask(dto,1L));
        verify(taskDao).findById(1L);
        verify(taskDao).update(any());
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskDao.findById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> trainerService.updateTask(new TaskDto(),1L));
        verify(taskDao).findById(1L);
    }

    @Test
    void testDeleteTask_Success() {
        when(taskDao.findById(1L)).thenReturn(task);
        assertDoesNotThrow(() -> trainerService.deleteTask(1L));
        verify(taskDao).findById(1L);
        verify(taskDao).delete(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskDao.findById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> trainerService.deleteTask(1L));
        verify(taskDao).findById(1L);
    }

    @Test
    void testMarkAttendance_Success() {
        AttendanceDto dto = new AttendanceDto(1L,1L,Date.valueOf(LocalDate.now()),true);
        when(attendanceDao.save(any())).thenReturn(attendance);
        Attendance saved = trainerService.markAttendance(dto);
        assertNotNull(saved);
        verify(attendanceDao).save(any());
    }

    @Test
    void testMarkAttendance_FutureDate() {
        AttendanceDto dto = new AttendanceDto(1L,1L,Date.valueOf(LocalDate.now().plusDays(2)),true);
        assertThrows(RuntimeException.class, () -> trainerService.markAttendance(dto));
    }

    @Test
    void testUpdatePerformance_Success() {
        PerformanceDto dto = new PerformanceDto(1L,1L,1L,"Trainer A","Good work",90);
        when(performanceDao.save(any())).thenReturn(performance);
        Performance saved = trainerService.updatePerformance(dto);
        assertNotNull(saved);
        verify(performanceDao).save(any());
    }

    @Test
    void testViewFeedback_Success() {
        when(feedbackDao.findByTrainerName("Trainer A")).thenReturn(List.of(feedback));
        List<Feedback> list = trainerService.viewFeedback("Trainer A");
        assertEquals(1,list.size());
        verify(feedbackDao).findByTrainerName("Trainer A");
    }

    @Test
    void testViewFeedback_NotFound() {
        when(feedbackDao.findByTrainerName("Trainer A")).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> trainerService.viewFeedback("Trainer A"));
        verify(feedbackDao).findByTrainerName("Trainer A");
    }

    @Test
    void testGetUsersByTrainerId_Success() {
        UserBatchDto dto = new UserBatchDto();
        dto.setUserId(1L);
        when(userBatchDao.getUsersByTrainerId(1)).thenReturn(List.of(dto));
        List<UserBatchDto> list = trainerService.getUsersByTrainerId(1);
        assertEquals(1,list.size());
        verify(userBatchDao).getUsersByTrainerId(1);
    }
    @Test
    void testGetUsersByTrainerId_NotFound() {
        when(userBatchDao.getUsersByTrainerId(1)).thenReturn(Collections.emptyList());
        List<UserBatchDto> list = trainerService.getUsersByTrainerId(1);
        assertTrue(list.isEmpty());
        verify(userBatchDao).getUsersByTrainerId(1);
    }


    @Test
    void testGetTasksByTrainerId_Success() {
        when(taskDao.findByTrainerId(1L)).thenReturn(List.of(task));
        List<Task> list = trainerService.getTasksByTrainerId(1L);
        assertEquals(1,list.size());
        verify(taskDao).findByTrainerId(1L);
    }

    @Test
    void testGetTasksByTrainerId_NotFound() {
        when(taskDao.findByTrainerId(1L)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> trainerService.getTasksByTrainerId(1L));
        verify(taskDao).findByTrainerId(1L);
    }

    @Test
    void testGetPerformanceByTrainerId_Success() {
        when(performanceDao.findByTrainerId(1L)).thenReturn(List.of(performance));
        List<Performance> list = trainerService.getPerformanceByTrainerId(1L);
        assertEquals(1,list.size());
        verify(performanceDao).findByTrainerId(1L);
    }

    @Test
    void testGetPerformanceByTrainerId_NotFound() {
        when(performanceDao.findByTrainerId(1L)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> trainerService.getPerformanceByTrainerId(1L));
        verify(performanceDao).findByTrainerId(1L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Task A", "Task B", "Task C"})
    void testParameterizedTaskDescriptions(String desc) {
        Task t = new Task();
        t.setDescription(desc);
        assertTrue(t.getDescription().length() >= 5);
    }
    
 // ✅ Minimum boundary (valid)
    @Test
    void testUpdatePerformance_MinimumBoundary() {
        PerformanceDto dto = new PerformanceDto(1L, 1L, 1L, "Trainer A", "Good work", 0);
 
        Performance expected = new Performance();
        expected.setId(1L);
        expected.setTaskEvaluationScore(0);
 
        when(performanceDao.save(any())).thenReturn(expected);
 
        Performance saved = trainerService.updatePerformance(dto);
 
        assertEquals(0, saved.getTaskEvaluationScore());
        verify(performanceDao).save(any());
    }
// ✅ Maximum boundary (valid)
    @Test
    void testUpdatePerformance_MaximumBoundary() {
        PerformanceDto dto = new PerformanceDto(1L, 1L, 1L, "Trainer A", "Excellent work", 100);
 
        Performance expected = new Performance();
        expected.setId(2L);
        expected.setTaskEvaluationScore(100);
 
        when(performanceDao.save(any())).thenReturn(expected);
 
        Performance saved = trainerService.updatePerformance(dto);
 
        assertEquals(100, saved.getTaskEvaluationScore());
        verify(performanceDao).save(any());
    }
// ❌ Below minimum boundary (invalid)
    @Test
    void testUpdatePerformance_BelowMinimumBoundary() {
        PerformanceDto dto = new PerformanceDto(1L, 1L, 1L, "Trainer A", "Too low", -1);
 
        assertThrows(IllegalArgumentException.class, () -> trainerService.updatePerformance(dto));
        verify(performanceDao, never()).save(any());
    }
// ❌ Above maximum boundary (invalid)
    @Test
    void testUpdatePerformance_AboveMaximumBoundary() {
        PerformanceDto dto = new PerformanceDto(1L, 1L, 1L, "Trainer A", "Too high", 101);
 
        assertThrows(IllegalArgumentException.class, () -> trainerService.updatePerformance(dto));
        verify(performanceDao, never()).save(any());
    }

}