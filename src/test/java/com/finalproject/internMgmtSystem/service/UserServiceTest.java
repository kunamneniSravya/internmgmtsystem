package com.finalproject.internMgmtSystem.service;

import com.finalproject.internMgmtSystem.dto.FeedbackDto;
import com.finalproject.internMgmtSystem.dto.RegisterUserDto;
import com.finalproject.internMgmtSystem.exception.ResourceNotFoundException;
import com.finalproject.internMgmtSystem.exception.UserAlreadyExistsException;
import com.finalproject.internMgmtSystem.model.*;
import com.finalproject.internMgmtSystem.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock private UserDao userDao;
    @Mock private TaskDao taskDao;
    @Mock private PerformanceDao performanceDao;
    @Mock private StipendDao stipendDao;
    @Mock private BatchDao batchDao;
    @Mock private FeedbackDao feedbackDao;
    @Mock private AttendanceDao attendanceDao;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private InternshipBatch batch;
    private User user;
    private Task task;
    private Performance performance;
    private Stipend stipend;
    private Feedback feedback;
    private Attendance attendance;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        batch = new InternshipBatch();
        batch.setBatchCode(1L);
        batch.setBatchName("Java Batch Dec 2025");
        batch.setAvailableSeats(45);
        batch.setTotalSeats(50);

        user = new User();
        user.setUserId(1L);
        user.setEmail("Sravi@gmail.com");
        user.setRole("USER");
        user.setBatchCode(1L);

        task = new Task();
        task.setId(1L);
        task.setUserId(1L);

        performance = new Performance();
        performance.setId(1L);
        performance.setUserId(1L);

        stipend = new Stipend();
        stipend.setId(1L);
        stipend.setUserId(1L);
        stipend.setPaymentDate(new Timestamp(System.currentTimeMillis()));

        feedback = new Feedback();
        feedback.setId(1L);
        feedback.setUserId(1L);
        feedback.setTrainerName("Sravya Kunamneni");
        feedback.setRating(5);

        attendance = new Attendance();
        attendance.setAttendanceId(1L);
        attendance.setUserId(1L);
        attendance.setBatchCode(1L);
        attendance.setDate(Date.valueOf("2025-01-10"));
        attendance.setPresent(true);
    }

    @Test
    void testRegisterUser_Success() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setUserName("Sravya");
        dto.setEmail("sravya@gmail.com");
        dto.setPassword("123");
        dto.setDob(Date.valueOf("2003-05-10"));
        dto.setContactNo("9876543210");
        dto.setAddress("Chennai");
        dto.setCollegeName("Vel Tech");
        dto.setGrade("A");
        dto.setMajor("AIML");
        dto.setTeam("Backend");
        dto.setBatchCode(1L);
        dto.setStartDate(Date.valueOf("2025-01-10"));
        dto.setEndDate(Date.valueOf("2025-06-10"));
        dto.setGraduatingYear(2026);
        dto.setResume("resume.pdf");
       

        when(userDao.findByEmail("sravya@gmail.com")).thenReturn(null);
        when(batchDao.findByBatchCode(1L)).thenReturn(batch);
        when(passwordEncoder.encode("pass")).thenReturn("ENC");
        when(userDao.save(any(User.class))).thenReturn(user);

        User saved = userService.registerUser(dto);

        assertNotNull(saved);
        assertEquals(1L, saved.getUserId());
        verify(userDao).findByEmail("sravya@gmail.com");
        verify(batchDao).findByBatchCode(1L);
        verify(batchDao).decrementAvailableSeats(1L);
        verify(passwordEncoder).encode("123");
        verify(userDao).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailExists() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setEmail("sravi@gmail.com");
        dto.setBatchCode(1L);

        when(userDao.findByEmail("sravi@gmail.com")).thenReturn(user);

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));
        verify(userDao).findByEmail("sravi@gmail.com");
        verify(batchDao, never()).findByBatchCode(anyLong());
        verify(userDao, never()).save(any());
    }

    @Test
    void testRegisterUser_BatchNotFound() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setEmail("sravya@gmail.com");
        dto.setBatchCode(99L);

        when(userDao.findByEmail("sravya@gmail.com")).thenReturn(null);
        when(batchDao.findByBatchCode(99L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.registerUser(dto));
        verify(userDao).findByEmail("sravya@gmail.com");
        verify(batchDao).findByBatchCode(99L);
        verify(userDao, never()).save(any());
    }

    @Test
    void testRegisterUser_NoSeats() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setEmail("sravya@gmail.com");
        dto.setBatchCode(1L);

        InternshipBatch full = new InternshipBatch();
        full.setBatchCode(1L);
        full.setAvailableSeats(0);

        when(userDao.findByEmail("sravya@gmail.com")).thenReturn(null);
        when(batchDao.findByBatchCode(1L)).thenReturn(full);

        assertThrows(ResourceNotFoundException.class, () -> userService.registerUser(dto));
        verify(userDao).findByEmail("sravya@gmail.com");
        verify(batchDao).findByBatchCode(1L);
        verify(batchDao, never()).decrementAvailableSeats(anyLong());
        verify(userDao, never()).save(any());
    }

    @Test
    void testGetTasks_Success() {
        when(taskDao.findByUserId(1L)).thenReturn(List.of(task));
        List<Task> list = userService.getTasks(1L);
        assertEquals(1, list.size());
        verify(taskDao).findByUserId(1L);
    }

    @Test
    void testGetTasks_Empty() {
        when(taskDao.findByUserId(1L)).thenReturn(List.of());
        List<Task> list = userService.getTasks(1L);
        assertTrue(list.isEmpty());
        verify(taskDao).findByUserId(1L);
    }

    @Test
    void testGetPerformance_Success() {
        when(performanceDao.findByUserId(1L)).thenReturn(List.of(performance));
        List<Performance> list = userService.getPerformance(1L);
        assertEquals(1, list.size());
        verify(performanceDao).findByUserId(1L);
    }

    @Test
    void testGetPerformance_Empty() {
        when(performanceDao.findByUserId(1L)).thenReturn(List.of());
        List<Performance> list = userService.getPerformance(1L);
        assertTrue(list.isEmpty());
        verify(performanceDao).findByUserId(1L);
    }

    @Test
    void testGetStipendHistory_Success() {
        when(stipendDao.findByUserId(1L)).thenReturn(List.of(stipend));
        List<Stipend> list = userService.getStipendHistory(1L);
        assertEquals(1, list.size());
        verify(stipendDao).findByUserId(1L);
    }

    @Test
    void testGetStipendHistory_Empty() {
        when(stipendDao.findByUserId(1L)).thenReturn(List.of());
        List<Stipend> list = userService.getStipendHistory(1L);
        assertTrue(list.isEmpty());
        verify(stipendDao).findByUserId(1L);
    }

    @Test
    void testGetBatchDetails_Success() {
        when(batchDao.findById(1L)).thenReturn(batch);
        InternshipBatch b = userService.getBatchDetails(1L);
        assertNotNull(b);
        verify(batchDao).findById(1L);
    }

    @Test
    void testGetBatchDetails_NotFound() {
        when(batchDao.findById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> userService.getBatchDetails(1L));
        verify(batchDao).findById(1L);
    }

    @Test
    void testSubmitFeedback_Success() {
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setBatchCode(1L);
        dto.setTrainerName("Sravya Kunamneni");
        dto.setFeedback("Very good");
        dto.setRating(5);

        when(feedbackDao.save(any(Feedback.class))).thenReturn(feedback);

        Feedback saved = userService.submitFeedback(dto);

        assertNotNull(saved);
        assertEquals(1L, saved.getUserId());
        verify(feedbackDao).save(any(Feedback.class));
    }

    @Test
    void testGetAttendance_Success() {
        when(attendanceDao.findByUserId(1L)).thenReturn(List.of(attendance));
        List<Attendance> list = userService.getAttendance(1L);
        assertEquals(1, list.size());
        verify(attendanceDao).findByUserId(1L);
    }

    @Test
    void testGetAttendance_Empty() {
        when(attendanceDao.findByUserId(1L)).thenReturn(List.of());
        List<Attendance> list = userService.getAttendance(1L);
        assertTrue(list.isEmpty());
        verify(attendanceDao).findByUserId(1L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "B", "C"})
    void testParameterizedGrades(String grade) {
        User u = new User();
        u.setGrade(grade);
        assertNotNull(u.getGrade());
    }
    
 // ✅ Minimum boundary (valid)
    @Test
    void testSubmitFeedback_MinimumRating() {
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setBatchCode(1L);
        dto.setTrainerName("TrainerX");
        dto.setFeedback("Okay");
        dto.setRating(1); // minimum boundary
 
        Feedback f = new Feedback();
        f.setId(99L);
        f.setRating(1);
 
        when(feedbackDao.save(any(Feedback.class))).thenReturn(f);
 
        Feedback saved = userService.submitFeedback(dto);
        assertEquals(1, saved.getRating());
    }
// ✅ Maximum boundary (valid)
    @Test
    void testSubmitFeedback_MaximumRating() {
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setBatchCode(1L);
        dto.setTrainerName("TrainerY");
        dto.setFeedback("Excellent");
        dto.setRating(5); // maximum boundary
 
        Feedback f = new Feedback();
        f.setId(100L);
        f.setRating(5);
 
        when(feedbackDao.save(any(Feedback.class))).thenReturn(f);
 
        Feedback saved = userService.submitFeedback(dto);
        assertEquals(5, saved.getRating());
    }
// ❌ Below minimum boundary (invalid)
    @Test
    void testSubmitFeedback_BelowMinimumRating() {
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setBatchCode(1L);
        dto.setTrainerName("TrainerZ");
        dto.setFeedback("Bad");
        dto.setRating(0); // invalid
 
        assertThrows(IllegalArgumentException.class, () -> userService.submitFeedback(dto));
    }
// ❌ Above maximum boundary (invalid)
    @Test
    void testSubmitFeedback_AboveMaximumRating() {
        FeedbackDto dto = new FeedbackDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setBatchCode(1L);
        dto.setTrainerName("TrainerZ");
        dto.setFeedback("Too good");
        dto.setRating(6); // invalid
 
        assertThrows(IllegalArgumentException.class, () -> userService.submitFeedback(dto));
    }
}