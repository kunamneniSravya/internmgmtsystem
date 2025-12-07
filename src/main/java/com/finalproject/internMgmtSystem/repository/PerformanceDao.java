package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Performance;
import java.util.List;

public interface PerformanceDao {

    Performance save(Performance performance);

    List<Performance> findByUserId(Long userId);

    List<Performance> findByTrainerId(Long trainerId);

    void deleteById(Long id);
    
    
}
