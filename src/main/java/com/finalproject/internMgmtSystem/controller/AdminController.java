package com.finalproject.internMgmtSystem.controller;

import com.finalproject.internMgmtSystem.dto.BatchDto;
import com.finalproject.internMgmtSystem.dto.RegisterTrainerDto;
import com.finalproject.internMgmtSystem.dto.StipendDto;
import com.finalproject.internMgmtSystem.model.InternshipBatch;
import com.finalproject.internMgmtSystem.model.Stipend;
import com.finalproject.internMgmtSystem.model.Trainer;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/trainers")
    public Trainer createTrainer(@Valid @RequestBody RegisterTrainerDto dto) {
        return adminService.registerTrainer(dto);
    }

    @PostMapping("/batches")
    public InternshipBatch createBatch(@Valid @RequestBody BatchDto dto) {
        return adminService.createBatch(dto);
    }

    @PostMapping("/stipend")
    public Stipend giveStipend(@Valid @RequestBody StipendDto dto) {
        return adminService.giveStipend(dto);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/trainers")
    public List<Trainer> getAllTrainers() {
        return adminService.getAllTrainers();
    }

    @GetMapping("/batches")
    public List<InternshipBatch> getAllBatches() {
        return adminService.getAllBatches();
    }

    // DELETE USER BY EMAIL
    @DeleteMapping("/user/email/{email}")
    public String deleteUserByEmail(@PathVariable String email) {
        adminService.deleteUserByEmail(email);
        return "User deleted successfully with email: " + email;
    }

    // DELETE TRAINER BY EMAIL
    @DeleteMapping("/trainer/email/{email}")
    public String deleteTrainerByEmail(@PathVariable String email) {
        adminService.deleteTrainerByEmail(email);
        return "Trainer deleted successfully with email: " + email;
    }

    // DELETE BATCH BY BATCH NAME
    @DeleteMapping("/batch/name/{batchName}")
    public String deleteBatchByName(@PathVariable String batchName) {
        adminService.deleteBatchByName(batchName);
        return "Batch deleted successfully with name: " + batchName;
    }
}
