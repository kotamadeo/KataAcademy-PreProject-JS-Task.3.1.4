package com.gmail.at.kotamadeo.services;

import com.gmail.at.kotamadeo.models.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> findAll();

    Role findById(long id);

    Role findByName(String name);

    void save(Role role);
}
