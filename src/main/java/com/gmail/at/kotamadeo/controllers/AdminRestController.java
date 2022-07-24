package com.gmail.at.kotamadeo.controllers;

import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> showAllUsers(@ModelAttribute("user") User user) {
        return userService.showAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id, @ModelAttribute("user") User user) {
        return userService.getUser(id);
    }


    @PostMapping
    public User createNewUser(@RequestBody User user) {
        userService.createNewUser(user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUserById(@RequestBody User user) {
        userService.updateUserById(user.getId(), user);
        return user;
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
        return "Ok";
    }
}
