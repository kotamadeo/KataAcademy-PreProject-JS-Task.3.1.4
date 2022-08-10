package com.gmail.at.kotamadeo;


import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.repositories.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {
    private final RoleRepository roleRepository;

    @Autowired
    RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    void testCreateReadUpdateDeleteAdmin() {
        Role role = new Role("ROLE_ADMIN");
        roleRepository.save(role);
        Iterable<Role> roles = roleRepository.findAll();
        Assertions.assertThat(roles).extracting(Role::getName).containsOnly("ROLE_ADMIN");
        role.setName("ROLE_USER");
        roleRepository.saveAndFlush(role);
        Assertions.assertThat(roles).extracting(Role::getName).containsOnly("ROLE_USER");
        roleRepository.deleteAll();
        Assertions.assertThat(roleRepository.findAll()).isEmpty();
    }

    @Test
    void testCreateReadUpdateDeleteUser() {
        Role role = new Role("ROLE_USER");
        roleRepository.save(role);
        Iterable<Role> roles = roleRepository.findAll();
        Assertions.assertThat(roles).extracting(Role::getName).containsOnly("ROLE_USER");
        role.setName("ROLE_ADMIN");
        roleRepository.saveAndFlush(role);
        Assertions.assertThat(roles).extracting(Role::getName).containsOnly("ROLE_ADMIN");
        roleRepository.deleteAll();
        Assertions.assertThat(roleRepository.findAll()).isEmpty();
    }
}
