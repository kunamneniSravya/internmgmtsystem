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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

	@Autowired
	private TrainerDao trainerDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BatchDao batchDao;

	@Autowired
	private StipendDao stipendDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailUtil emailUtil;

	public Trainer registerTrainer(RegisterTrainerDto dto) {

		Trainer exists = trainerDao.findByEmail(dto.getEmail());
		if (exists != null) {
			throw new UserAlreadyExistsException("Trainer with email already exists: " + dto.getEmail());
		}

		Trainer trainer = new Trainer();
		trainer.setName(dto.getName());
		trainer.setEmail(dto.getEmail());
		trainer.setPassword(passwordEncoder.encode(dto.getPassword()));
		trainer.setContact(dto.getContact());
		trainer.setExperience(dto.getExperience());
		trainer.setSkills(dto.getSkills());
	
		trainer.setBio(dto.getBio());
		trainer.setRole("TRAINER");

		Trainer saved = trainerDao.save(trainer);

		// send email (you already had this util)
		emailUtil.sendEmail(dto.getEmail(), "Trainer Account Created",
				"Hello " + dto.getName() + ",\n\n" + "Your trainer account has been created.\n\n" + "Email: "
						+ dto.getEmail() + "\n" + "Password (temporary): " + dto.getPassword() + "\n\n"
						+ "Regards,\nTeam");

		return saved;
	}

	public InternshipBatch createBatch(BatchDto dto) {
		InternshipBatch batch = new InternshipBatch();

		batch.setBatchName(dto.getBatchName());
		batch.setCourseName(dto.getCourseName());
		batch.setTrainerId(dto.getTrainerId());
		batch.setStartDate(dto.getStartDate());
		batch.setEndDate(dto.getEndDate());
		batch.setTotalSeats(dto.getTotalSeats());
		batch.setAvailableSeats(dto.getAvailableSeats());

		return batchDao.save(batch);
	}

	public Stipend giveStipend(StipendDto dto) {

		User user = userDao.findById(dto.getUserId());
		if (user == null) {
			throw new ResourceNotFoundException("User not found for stipend: " + dto.getUserId());
		}

		Stipend stipend = new Stipend();
		stipend.setUserId(dto.getUserId());
		stipend.setUserName(dto.getUserName());
		stipend.setPaymentMode(dto.getPaymentMode());
		stipend.setAmount(dto.getAmount());

		return stipendDao.save(stipend);
	}

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public List<Trainer> getAllTrainers() {
		return trainerDao.findAll();
	}

	public List<InternshipBatch> getAllBatches() {
		return batchDao.findAll();
	}

	public void deleteTrainer(Long id) {
		if (trainerDao.findById(id) == null) {
			throw new ResourceNotFoundException("Trainer not found with ID: " + id);
		}
		trainerDao.deleteById(id);
	}

	public void deleteUser(Long id) {
		if (userDao.findById(id) == null) {
			throw new ResourceNotFoundException("User not found with ID: " + id);
		}
		userDao.deleteById(id);
	}

	// --------- NEW: delete by email/name helpers ----------

	// DELETE USER BY EMAIL
	public void deleteUserByEmail(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("User not found with email: " + email);
		}
		userDao.deleteById(user.getUserId());
	}

	// DELETE TRAINER BY EMAIL
	public void deleteTrainerByEmail(String email) {
		Trainer trainer = trainerDao.findByEmail(email);
		if (trainer == null) {
			throw new ResourceNotFoundException("Trainer not found with email: " + email);
		}
		trainerDao.deleteByEmail(email);
	}

	// DELETE BATCH BY NAME
	public void deleteBatchByName(String batchName) {
		InternshipBatch batch = batchDao.findByName(batchName);
		if (batch == null) {
			throw new ResourceNotFoundException("Batch not found with name: " + batchName);
		}
		batchDao.delete(batch.getBatchCode());
	}
}
