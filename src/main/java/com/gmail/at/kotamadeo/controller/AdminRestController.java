package com.gmail.at.kotamadeo.controller;

import com.gmail.at.kotamadeo.exceptions.InfoException;
import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.services.RoleService;
import com.gmail.at.kotamadeo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> users = userService.findAll();
        return users != null ?
                new ResponseEntity<>(users, HttpStatus.FOUND) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<InfoException> addNewUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = getErrorsList(bindingResult);
            return new ResponseEntity<>(new InfoException(errors), HttpStatus.BAD_REQUEST);
        }
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<InfoException> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = getErrorsList(bindingResult);
            return new ResponseEntity<>(new InfoException(errors), HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUser(Authentication auth) {
        return new ResponseEntity<>((User) auth.getPrincipal(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getAllRoles() {
        Set<Role> roles = roleService.findAll();
        return roles != null ?
                new ResponseEntity<>(roles, HttpStatus.FOUND) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    private String getErrorsList(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}


