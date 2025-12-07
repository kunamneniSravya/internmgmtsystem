package com.finalproject.internMgmtSystem.service;

import com.finalproject.internMgmtSystem.dto.BatchDto;
import com.finalproject.internMgmtSystem.dto.RegisterTrainerDto;
import com.finalproject.internMgmtSystem.dto.StipendDto;
import com.finalproject.internMgmtSystem.exception.ResourceNotFoundException;
import com.finalproject.internMgmtSystem.exception.UserAlreadyExistsException;
import com.finalproject.internMgmtSystem.model.InternshipBatch;
import com.finalproject.internMgmtSystem.model.Stipend;
import com.finalproject.internMgmtSystem.model.Trainer;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.repository.*;
import com.finalproject.internMgmtSystem.util.EmailUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock private TrainerDao trainerDao;
    @Mock private UserDao userDao;
    @Mock private BatchDao batchDao;
    @Mock private StipendDao stipendDao;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private EmailUtil emailUtil;

    @InjectMocks
    private AdminService adminService;

    private Trainer trainer;
    private User user;
    private InternshipBatch batch;
    private Stipend stipend;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        trainer = new Trainer();
        trainer.setTrainerId(1L);
        trainer.setEmail("trainer@mail.com");

        user = new User();
        user.setUserId(1L);
        user.setEmail("u@mail.com");

        batch = new InternshipBatch();
        batch.setBatchCode(1L);
        batch.setBatchName("B1");

        stipend = new Stipend();
        stipend.setId(1L);
        stipend.setUserId(1L);
    }

    // ================= REGISTER TRAINER ==================
    @Test
    void testRegisterTrainer_Success() {
        RegisterTrainerDto dto = new RegisterTrainerDto();
        dto.setName("Sravya");
        dto.setEmail("new@mail.com");
        dto.setPassword("123");

        when(trainerDao.findByEmail("new@mail.com")).thenReturn(null);
        when(passwordEncoder.encode("123")).thenReturn("ENC");
        when(trainerDao.save(any())).thenReturn(trainer);

        Trainer saved = adminService.registerTrainer(dto);

        assertNotNull(saved);
        verify(trainerDao).findByEmail("new@mail.com");
        verify(passwordEncoder).encode("123");
        verify(trainerDao).save(any());
        verify(emailUtil).sendEmail(eq("new@mail.com"), anyString(), anyString());
    }

    @Test
    void testRegisterTrainer_EmailExists() {
        RegisterTrainerDto dto = new RegisterTrainerDto();
        dto.setEmail("trainer@mail.com");

        when(trainerDao.findByEmail("trainer@mail.com")).thenReturn(trainer);

        assertThrows(UserAlreadyExistsException.class, () -> adminService.registerTrainer(dto));

        verify(trainerDao).findByEmail("trainer@mail.com");
    }

    // ================= CREATE BATCH ==================
    @Test
    void testCreateBatch_Success() {
        BatchDto dto = new BatchDto( "B1", "Java", 1L,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                10, 10);

        when(batchDao.save(any())).thenReturn(batch);

        InternshipBatch saved = adminService.createBatch(dto);

        assertEquals("B1", saved.getBatchName());
        verify(batchDao).save(any());
    }
    
    @Test
    void testCreateBatch_Failure() {
        BatchDto dto = new BatchDto(
                "B1", "Java", 1L,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                10, 10);

        when(batchDao.save(any())).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> adminService.createBatch(dto));
        verify(batchDao).save(any());
    }

    // ================= GIVE STIPEND ==================
    @Test
    void testGiveStipend_Success() {
        StipendDto dto = new StipendDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setPaymentMode("UPI");
        dto.setAmount(8000.0);

        when(userDao.findById(1L)).thenReturn(user);
        when(stipendDao.save(any())).thenReturn(stipend);

        Stipend saved = adminService.giveStipend(dto);

        assertNotNull(saved);
        verify(userDao).findById(1L);
        verify(stipendDao).save(any());
    }

    @Test
    void testGiveStipend_UserNotFound() {
        StipendDto dto = new StipendDto();
        dto.setUserId(99L);

        when(userDao.findById(99L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> adminService.giveStipend(dto));

        verify(userDao).findById(99L);
    }

    // ================= GET ALL USERS ==================
    @Test
    void testGetAllUsers_Success() {
        when(userDao.findAll()).thenReturn(List.of(user));

        List<User> list = adminService.getAllUsers();

        assertEquals(1, list.size());
        verify(userDao).findAll();
    }

    @Test
    void testGetAllUsers_Empty() {
        when(userDao.findAll()).thenReturn(Collections.emptyList());

        List<User> list = adminService.getAllUsers();

        assertEquals(0, list.size());
        verify(userDao).findAll();
    }

    // ================= GET ALL TRAINERS ==================
    @Test
    void testGetAllTrainers_Success() {
        when(trainerDao.findAll()).thenReturn(List.of(trainer));

        List<Trainer> list = adminService.getAllTrainers();

        assertEquals(1, list.size());
        verify(trainerDao).findAll();
    }

    @Test
    void testGetAllTrainers_Empty() {
        when(trainerDao.findAll()).thenReturn(Collections.emptyList());

        List<Trainer> list = adminService.getAllTrainers();

        assertEquals(0, list.size());
        verify(trainerDao).findAll();
    }

    // ================= GET ALL BATCHES ==================
    @Test
    void testGetAllBatches_Success() {
        when(batchDao.findAll()).thenReturn(List.of(batch));

        List<InternshipBatch> list = adminService.getAllBatches();

        assertEquals(1, list.size());
        verify(batchDao).findAll();
    }

    @Test
    void testGetAllBatches_Empty() {
        when(batchDao.findAll()).thenReturn(Collections.emptyList());

        List<InternshipBatch> list = adminService.getAllBatches();

        assertEquals(0, list.size());
        verify(batchDao).findAll();
    }

    // ================= DELETE USER ==================
    @Test
    void testDeleteUser_Success() {
        when(userDao.findById(1L)).thenReturn(user);

        assertDoesNotThrow(() -> adminService.deleteUser(1L));

        verify(userDao).findById(1L);
        verify(userDao).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userDao.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> adminService.deleteUser(1L));

        verify(userDao).findById(1L);
    }

    // ================= DELETE TRAINER ==================
    @Test
    void testDeleteTrainer_Success() {
        when(trainerDao.findById(1L)).thenReturn(trainer);

        assertDoesNotThrow(() -> adminService.deleteTrainer(1L));

        verify(trainerDao).findById(1L);
        verify(trainerDao).deleteById(1L);
    }

    @Test
    void testDeleteTrainer_NotFound() {
        when(trainerDao.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> adminService.deleteTrainer(1L));

        verify(trainerDao).findById(1L);
    }

    // ================= DELETE BY EMAIL ==================
    @Test
    void testDeleteUserByEmail_Success() {
        when(userDao.findByEmail("u@mail.com")).thenReturn(user);

        assertDoesNotThrow(() -> adminService.deleteUserByEmail("u@mail.com"));

        verify(userDao).findByEmail("u@mail.com");
        verify(userDao).deleteById(1L);
    }

    @Test
    void testDeleteUserByEmail_NotFound() {
        when(userDao.findByEmail("x@mail.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> adminService.deleteUserByEmail("x@mail.com"));

        verify(userDao).findByEmail("x@mail.com");
    }

    @Test
    void testDeleteTrainerByEmail_Success() {
        when(trainerDao.findByEmail("trainer@mail.com")).thenReturn(trainer);

        assertDoesNotThrow(() -> adminService.deleteTrainerByEmail("trainer@mail.com"));

        verify(trainerDao).findByEmail("trainer@mail.com");
        verify(trainerDao).deleteByEmail("trainer@mail.com");
    }

    @Test
    void testDeleteTrainerByEmail_NotFound() {
        when(trainerDao.findByEmail("x@mail.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> adminService.deleteTrainerByEmail("x@mail.com"));

        verify(trainerDao).findByEmail("x@mail.com");
    }

    @Test
    void testDeleteBatchByName_Success() {
        when(batchDao.findByName("B1")).thenReturn(batch);

        assertDoesNotThrow(() -> adminService.deleteBatchByName("B1"));

        verify(batchDao).findByName("B1");
        verify(batchDao).delete(1L);
    }

    @Test
    void testDeleteBatchByName_NotFound() {
        when(batchDao.findByName("XX")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> adminService.deleteBatchByName("XX"));

        verify(batchDao).findByName("XX");
    }

    @ParameterizedTest
    @ValueSource(strings = {"UPI", "CASH", "BANK"})
    void testParameterizedPaymentModes(String mode) {
        Stipend s = new Stipend();
        s.setPaymentMode(mode);
        assertNotNull(s.getPaymentMode());
    }
    
 // ✅ Minimum boundary (valid)
    @Test
    void testGiveStipend_MinimumBoundaryAmount() {
        StipendDto dto = new StipendDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setPaymentMode("UPI");
        dto.setAmount(5000.0); // minimum valid amount
 
        when(userDao.findById(1L)).thenReturn(user);
        when(stipendDao.save(any(Stipend.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
 
        Stipend saved = adminService.giveStipend(dto);
 
        assertNotNull(saved);
        assertEquals(5000.0, saved.getAmount());
        verify(userDao).findById(1L);
        verify(stipendDao).save(any());
    }
 
// ❌ Below minimum boundary (invalid)
    @Test
    void testGiveStipend_BelowMinimumBoundaryAmount() {
        StipendDto dto = new StipendDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setPaymentMode("UPI");
        dto.setAmount(2000.0); // invalid, below minimum
 
        when(userDao.findById(1L)).thenReturn(user);
 
        assertThrows(IllegalArgumentException.class, () -> adminService.giveStipend(dto));
        verify(stipendDao, never()).save(any());
    }
// ❌ Above minimum boundary (valid)
    @Test
    void testGiveStipend_AboveMinimumBoundaryAmount() {
        StipendDto dto = new StipendDto();
        dto.setUserId(1L);
        dto.setUserName("Sravya");
        dto.setPaymentMode("UPI");
        dto.setAmount(7500.0); // valid, above minimum
 
        when(userDao.findById(1L)).thenReturn(user);
        // Return the actual stipend passed into save()
        when(stipendDao.save(any(Stipend.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
 
        Stipend saved = adminService.giveStipend(dto);
 
        assertNotNull(saved);
        assertEquals(7500.0, saved.getAmount());
        verify(userDao).findById(1L);
        verify(stipendDao).save(any());
    }
}