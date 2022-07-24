package com.gmail.at.kotamadeo.services;

import com.gmail.at.kotamadeo.dao.UserDao;
import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDAO;

    @Autowired
    public UserServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> showAllUsers() {
        return userDAO.showAllUsers();
    }

    @Override
    public List<Role> showAllRoles() {
        return userDAO.showAllRoles();
    }

    @Override
    public User getUser(long id) {
        return userDAO.getUser(id);
    }

    @Override
    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    @Override
    public void createNewUser(User user) {
        userDAO.createNewUser(user);
    }

    @Override
    public void createNewRole(Role role) {
        userDAO.createNewRole(role);
    }

    @Override
    public void updateUserById(long id, User user) {
        userDAO.updateUserById(id, user);
    }

    @Override
    public void deleteUserById(Long id) {
        userDAO.deleteUserById(id);
    }

    @Override
    public Set<Role> findRolesById(String roleID) {
        Set<Role> roles = new HashSet<>();
        for (Role role : showAllRoles()) {
            if (roleID.contains(String.valueOf(role.getId()))) {
                System.out.println(role);
                roles.add(role);
            }
        }
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
        return user;
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public boolean emailCheck(String email) {
        return !userDAO.getAllEmailList().contains(email);
    }
}
