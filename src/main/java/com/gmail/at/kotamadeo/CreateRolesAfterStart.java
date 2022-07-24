package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CreateRolesAfterStart {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CreateRolesAfterStart(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStart() {
        if (userService.showAllRoles().isEmpty()) {
            Role admin = new Role("ROLE_ADMIN");
            Role user = new Role("ROLE_USER");
            userService.createNewRole(admin);
            userService.createNewRole(user);
            Set<Role> roles = new HashSet<>();
            roles.add(user);
            User simpleUser = new User("Гладушева", "Елена", "ж", "elenkaglad@mail.ru", (byte) 28);
            simpleUser.setRoles(roles);
            simpleUser.setUsername("elenka");
            simpleUser.setPassword(passwordEncoder.encode("elenkaglad@mail.ru"));
            roles = new HashSet<>();
            roles.add(user);
            roles.add(admin);
            User adminUser = new User("Кузнецов", "Игорь", "м", "kotamadeo@gmail.com", (byte) 25);
            adminUser.setRoles(roles);
            adminUser.setUsername("kotamadeo");
            adminUser.setPassword(passwordEncoder.encode("kotamadeo@gmail.com"));
            userService.createNewUser(simpleUser);
            userService.createNewUser(adminUser);
            System.out.println();
            System.out.println("*****************************");
            System.out.println("User:\nL: elenkaglad@mail.ru\nP: elenkaglad@mail.ru\n\nAdmin:\nL:kotamadeo@gmail.com\nP:kotamadeo@gmail.com");
            System.out.println("*****************************");
        }
    }
}
