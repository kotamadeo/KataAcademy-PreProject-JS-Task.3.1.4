package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.services.RoleServiceImpl;
import com.gmail.at.kotamadeo.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CreateRolesAfterStart {
    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CreateRolesAfterStart(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl,
                                 @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStart() {
        if (roleService.findAll().isEmpty()) {
            Role admin = new Role("ROLE_ADMIN");
            Role user = new Role("ROLE_USER");
            roleService.save(admin);
            roleService.save(user);
            Set<Role> roles = new HashSet<>();
            roles.add(user);
            User simpleUser = new User("Гладушева", "Елена", "elenkaglad@mail.ru", (byte) 28);
            simpleUser.setRoles(roles);
            simpleUser.setPassword(passwordEncoder.encode("elenkaglad@mail.ru"));
            roles = new HashSet<>();
            roles.add(user);
            roles.add(admin);
            User adminUser = new User("Кузнецов", "Игорь", "kotamadeo@gmail.com", (byte) 25);
            adminUser.setRoles(roles);
            adminUser.setPassword(passwordEncoder.encode("kotamadeo@gmail.com"));
            userServiceImpl.saveUser(simpleUser);
            userServiceImpl.saveUser(adminUser);
            System.out.println();
            System.out.println("*****************************");
            System.out.println("User:\nL: elenkaglad@mail.ru\nP: elenkaglad@mail.ru\n\nAdmin:\nL:kotamadeo@gmail.com\nP:kotamadeo@gmail.com");
            System.out.println("*****************************");
        }
    }
}
