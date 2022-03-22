package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("formObject", new User());
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user){
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
}
