package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Feedback;
import java.util.List;

public interface FeedbackDao {

    Feedback save(Feedback feedback);

    List<Feedback> findByTrainerName(String trainerName);

    List<Feedback> findByUserId(Long userId);

    List<Feedback> findAll();
    
    List<Feedback> findByTrainerId(Long trainerId);
}
