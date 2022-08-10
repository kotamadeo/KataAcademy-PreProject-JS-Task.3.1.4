package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.repositories.UserRepository;
import com.gmail.at.kotamadeo.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void findAllUsers() {
        List<User> users = new ArrayList<>() {{
            add(new User("Ivan", "Ivanov", "M", "Test@mail.ru", "ivan", (byte) 30));
            add(new User("Ivan", "Ivanov", "F", "Test2@mail.ru", "ivan", (byte) 24));
        }};
        when(userRepository.findAll()).thenReturn(users);
        List<User> userList = userService.findAll();
        assertEquals(2, userList.size());
        verify(userRepository, only()).findAll();
    }

    @Test
    void createNewUser() {
        User user = new User("Ivan", "Ivanov", "M", "Test@mail.ru", "ivan", (byte) 30);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword("123");
        bCryptPasswordEncoder.encode(user.getPassword());
        userService.createUser(user);
        verify(userRepository, only()).save(user);
    }

    @Test
    void updateUser() {
        User user = new User("Ivan", "Ivanov", "M", "Test@mail.ru", "ivan", (byte) 30);
        user.setName("Diana");
        user.setSex("F");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword("123");
        bCryptPasswordEncoder.encode(user.getPassword());
        userService.updateUser(user);
        verify(userRepository, only()).saveAndFlush(user);
    }

    @Test
    void findUserById() {
        User user = new User("Ivan", "Ivanov", "M", "Test@mail.ru", "ivan", (byte) 30);
        user.setId(1L);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword("123");
        bCryptPasswordEncoder.encode(user.getPassword());
        userService.findById(user.getId());
        verify(userRepository, only()).findById(user.getId());
    }

    @Test
    void deleteUserById() {
        User user = new User("Ivan", "Ivanov", "M", "Test@mail.ru", "ivan", (byte) 30);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user.setId(1L);
        user.setRoles(roles);
        user.setPassword("123");
        bCryptPasswordEncoder.encode(user.getPassword());
        userService.deleteById(user.getId());
        verify(userRepository, only()).deleteById(user.getId());
    }
}
