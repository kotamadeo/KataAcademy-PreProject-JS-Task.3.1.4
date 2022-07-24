package com.gmail.at.kotamadeo.dao;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    List<User> showAllUsers();

    List<Role> showAllRoles();

    List<String> getAllEmailList();

    User getUser(long id);

    User getUser(String email);

    void createNewUser(User user);

    void createNewRole(Role role);

    void updateUserById(long id, User user);

    void deleteUserById(Long id);

}
