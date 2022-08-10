package com.gmail.at.kotamadeo.services;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void createUser(User user) {
        checkRolesAndPass(user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        checkRolesAndPass(user);
        userRepository.saveAndFlush(user);
    }

    private void checkRolesAndPass(User user) {
        Set<Role> roles = user.getRoles();
        if (roles.isEmpty()) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        if (!user.getPassword().contains("$2a$12$")) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

}
