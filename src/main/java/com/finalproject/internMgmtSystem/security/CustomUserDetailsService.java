package com.finalproject.internMgmtSystem.security;

import com.finalproject.internMgmtSystem.model.Trainer;
import com.finalproject.internMgmtSystem.model.User;
import com.finalproject.internMgmtSystem.repository.TrainerDao;
import com.finalproject.internMgmtSystem.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TrainerDao trainerDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.findByEmail(email);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))   // FIXED
            );
        }

        Trainer trainer = trainerDao.findByEmail(email);
        if (trainer != null) {
            return new org.springframework.security.core.userdetails.User(
                    trainer.getEmail(),
                    trainer.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + trainer.getRole())) // FIXED
            );
        }

        throw new UsernameNotFoundException("User not found: " + email);
    }
}
