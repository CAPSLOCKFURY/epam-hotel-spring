package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.aop.ValidateFormWithPRG;
import com.example.epamhotelspring.dto.RoomHistoryDTO;
import com.example.epamhotelspring.forms.AddBalanceForm;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.RoomService;
import com.example.epamhotelspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final RoomService roomService;

    @GetMapping("")
    public String userProfile(Model model, @AuthenticationPrincipal User user) {
        User userFromDb = userService.getUserById(user.getId());
        model.addAttribute("user", userFromDb);
        return "profile";
    }

    @GetMapping("/add-balance")
    public String showAddBalance(Model model){
        if(!model.containsAttribute("addBalanceForm")){
            model.addAttribute("addBalanceForm", new AddBalanceForm());
        }
        return "add-balance";
    }

    @ValidateFormWithPRG(formName = "addBalanceForm", redirectUrlOnError = "redirect:/profile/add-balance")
    @PostMapping("/add-balance")
    public String addBalance(@Valid @ModelAttribute("addBalanceForm") AddBalanceForm addBalanceForm, BindingResult bindingResult,
                             RedirectAttributes attrs, @AuthenticationPrincipal User user){
        userService.addBalance(addBalanceForm.getAmount(), user);
        return "redirect:/profile";
    }


    @GetMapping("/room-history")
    public String getUserRoomHistory(Model model, @AuthenticationPrincipal User user){
        List<RoomHistoryDTO> roomHistory = roomService.getUserRoomHistory(user.getId());
        model.addAttribute("rooms", roomHistory);
        return "room-history";
    }

    @Autowired
    public ProfileController(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }
}
