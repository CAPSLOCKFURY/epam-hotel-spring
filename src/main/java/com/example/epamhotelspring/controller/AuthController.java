package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.forms.UserForm;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.UserService;
import com.example.epamhotelspring.validation.utils.FlashAttributePrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegister(Model model){
        if(!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new UserForm());
        }
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("registrationForm") UserForm userForm,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        FlashAttributePrg errorsPrg = new FlashAttributePrg(bindingResult, redirectAttributes, "registrationForm", userForm);
        boolean hasErrors = errorsPrg.processErrorsIfExists();
        if(hasErrors){
            return "redirect:/register";
        }
        userService.registerUser(new User(userForm));
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
