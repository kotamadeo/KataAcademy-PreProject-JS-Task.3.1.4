package com.gmail.at.kotamadeo.services;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> showAllUsers();

    List<Role> showAllRoles();

    User getUser(long id);

    User getUser(String username);

    void createNewUser(User user);

    void createNewRole(Role role);

    void updateUserById(long id, User user);

    void deleteUserById(Long id);

    Set<Role> findRolesById(String roleID);

    String getCurrentUsername();

    boolean emailCheck(String email);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
