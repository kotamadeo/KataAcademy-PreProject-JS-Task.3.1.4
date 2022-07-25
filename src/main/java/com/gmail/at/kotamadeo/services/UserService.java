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

    User getUser(String email);

    void createNewUser(User user);

    void createNewRole(Role role);

    void updateUser(User user);

    void deleteUserById(Long id);

    Set<Role> findRolesByID(long id);

    String getCurrentUsername();

    //boolean emailCheck(String email);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
