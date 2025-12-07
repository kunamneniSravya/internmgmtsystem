package com.finalproject.internMgmtSystem.repository;

import com.finalproject.internMgmtSystem.model.User;
import java.util.List;

public interface UserDao {

    User save(User user);

    User findByEmail(String email);

    User findById(Long id);

    List<User> findAll();

    void update(User user);

    void deleteById(Long id);
}
