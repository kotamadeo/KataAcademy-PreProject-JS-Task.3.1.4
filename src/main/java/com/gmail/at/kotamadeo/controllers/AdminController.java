package com.gmail.at.kotamadeo.controllers;

import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping()
    public String showAllUsers(@AuthenticationPrincipal User principal,
                               @ModelAttribute("user") User user, Model model) {
        model.addAttribute("principal", principal);
        model.addAttribute("users", userService.showAllUsers());
        model.addAttribute("listRoles", userService.showAllRoles());
        return "admins/admin";
    }


    @PostMapping
    public String createNewUser(@RequestParam("rolesId") String rolesId, @ModelAttribute("user") @Valid User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userService.findRolesById(rolesId));
        userService.createNewUser(user);
        return "redirect:/admin";
    }


    @PatchMapping
    public String updateUserById(@RequestParam("id") long id,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("name") String name,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("email") String email,
                                 @RequestParam("age") byte age,
                                 @RequestParam("username") String username,
                                 @RequestParam(value = "password", required = false) String password,
                                 @RequestParam(value = "rolesId", required = false) String roleID,
                                 @ModelAttribute("user") @Valid  User user) {
        user = new User(id, surname, name, sex, email, age, username);
        if (password != null) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }
        if (roleID != null) {
            user.setRoles(userService.findRolesById(roleID));
        }
        userService.updateUserById(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping
    public String deleteUserById(@RequestParam("id") long id, @ModelAttribute("user") User user) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}