package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.InternshipBatch;
import java.util.List;

public interface BatchDao {

    InternshipBatch save(InternshipBatch batch);

    InternshipBatch findById(Long batchCode);

    List<InternshipBatch> findAll();

    List<InternshipBatch> findByTrainerId(Long trainerId);

    void update(InternshipBatch batch);

    void delete(Long batchCode);

    InternshipBatch findByName(String batchName);
    
    InternshipBatch findByBatchCode(Long batchCode);

    void decrementAvailableSeats(Long batchCode);
}
