package com.gmail.at.kotamadeo.repositories;

import com.gmail.at.kotamadeo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByRoleTitle(String roleTitle);
}