package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.Stipend;
import java.util.List;

public interface StipendDao {

    Stipend save(Stipend stipend);

    List<Stipend> findByUserId(Long userId);

    List<Stipend> findAll();
}
