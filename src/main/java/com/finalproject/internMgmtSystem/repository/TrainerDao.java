package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Trainer;
import java.util.List;

public interface TrainerDao {

    Trainer save(Trainer trainer);

    Trainer findByEmail(String email);

    Trainer findById(Long id);

    List<Trainer> findAll();

    void update(Trainer trainer);

    void deleteById(Long id);

    void deleteByEmail(String email);
}
