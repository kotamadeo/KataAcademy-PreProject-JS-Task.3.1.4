package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testCreateReadUpdateDelete() {
        User user = new User("Ivan", "Ivanov", "M", "Test@mail.ru", "ivan", (byte) 30);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword("123");
        userRepository.save(user);
        Iterable<User> users = userRepository.findAll();
        Assertions.assertThat(users).extracting(User::getSurname).containsOnly("Ivan");
        user.setName("Petr");
        userRepository.saveAndFlush(user);
        Assertions.assertThat(users).extracting(User::getName).containsOnly("Petr");
        userRepository.deleteAll();
        Assertions.assertThat(userRepository.findAll()).isEmpty();
    }
}
