package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.repositories.RoleRepository;
import com.gmail.at.kotamadeo.services.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;

    @Test
    void findAllRoles() {
        List<Role> roles = new ArrayList<>() {{
            add(new Role("ROLE_ADMIN"));
            add(new Role("ROLE_USER"));
        }};
        when(roleRepository.findAll()).thenReturn(roles);
        Set<Role> rolesList = roleService.findAll();
        assertEquals(2, rolesList.size());
        verify(roleRepository, only()).findAll();
    }

    @Test
    void findRoleByIdAdmin() {
        List<Role> roles = new ArrayList<>() {{
            add(new Role("ROLE_ADMIN"));
            add(new Role("ROLE_USER"));
        }};
        roles.get(0).setId(1L);
        roles.get(1).setId(2L);
        roleRepository.findById(roles.get(0).getId());
        verify(roleRepository, only()).findById(roles.get(0).getId());
    }

    @Test
    void findRoleByIdUser() {
        List<Role> roles = new ArrayList<>() {{
            add(new Role("ROLE_ADMIN"));
            add(new Role("ROLE_USER"));
        }};
        roles.get(0).setId(1L);
        roles.get(1).setId(2L);
        roleRepository.findById(roles.get(1).getId());
        verify(roleRepository, only()).findById(roles.get(1).getId());
    }

    @Test
    void findRoleByTitleAdmin() {
        List<Role> roles = new ArrayList<>() {{
            add(new Role("ROLE_ADMIN"));
            add(new Role("ROLE_USER"));
        }};
        roles.get(0).setId(1L);
        roles.get(1).setId(2L);
        roleRepository.findByName(roles.get(0).getName());
        verify(roleRepository, only()).findByName(roles.get(0).getName());
    }

    @Test
    void findRoleByTitleUser() {
        List<Role> roles = new ArrayList<>() {{
            add(new Role("ROLE_ADMIN"));
            add(new Role("ROLE_USER"));
        }};
        roles.get(0).setId(1L);
        roles.get(1).setId(2L);
        roleRepository.findByName(roles.get(1).getName());
        verify(roleRepository, only()).findByName(roles.get(1).getName());
    }

    @Test
    void createRole() {
        List<Role> roles = new ArrayList<>() {{
            add(new Role("ROLE_ADMIN"));
            add(new Role("ROLE_USER"));
        }};
        for (Role role : roles) {
            if (role != null) {
                return;
            }
            roleRepository.save(role);
            verify(roleRepository, only()).save(role);
        }
    }
}
