package com.gmail.at.kotamadeo.services;

import com.gmail.at.kotamadeo.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findById(Long userId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteById(Long userId);
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
