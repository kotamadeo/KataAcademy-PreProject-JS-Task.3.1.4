package com.gmail.at.kotamadeo.controllers;

import com.gmail.at.kotamadeo.models.User;
import com.gmail.at.kotamadeo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUser(@AuthenticationPrincipal User principal, Model model) {
        model.addAttribute("user", principal);
        return "users/user";
    }
}