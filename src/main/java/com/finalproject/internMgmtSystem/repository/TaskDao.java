package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Task;
import java.util.List;

public interface TaskDao {

    Task save(Task task);

    Task findById(Long id);

    List<Task> findByUserId(Long userId);

    List<Task> findByTrainerId(Long trainerId);

    void update(Task task);

    void delete(Long id);
}
