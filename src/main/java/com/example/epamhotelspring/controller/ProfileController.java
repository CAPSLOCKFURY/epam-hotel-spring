package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @GetMapping("")
    public String userProfile(Model model, Authentication auth) {
        User user = (User) auth.getPrincipal();
        User userFromDb = userService.getUserById(user.getId());
        model.addAttribute("user", userFromDb);
        return "profile";
    }

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }
}
