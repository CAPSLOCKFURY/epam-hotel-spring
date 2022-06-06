package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.aop.ValidateFormWithPRG;
import com.example.epamhotelspring.dto.RoomHistoryDTO;
import com.example.epamhotelspring.forms.AddBalanceForm;
import com.example.epamhotelspring.forms.UserUpdateForm;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.RoomService;
import com.example.epamhotelspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @ValidateFormWithPRG(formName = "addBalanceForm", redirectUrlOnError = "'redirect:/profile/add-balance'")
    @PostMapping("/add-balance")
    public String addBalance(@Valid @ModelAttribute("addBalanceForm") AddBalanceForm addBalanceForm, BindingResult bindingResult,
                             RedirectAttributes attrs, @AuthenticationPrincipal User user){
        userService.addBalance(addBalanceForm.getAmount(), user);
        return "redirect:/profile";
    }


    @GetMapping("/room-history")
    public String getUserRoomHistory(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal User user){
        Page<RoomHistoryDTO> roomHistory = roomService.getUserRoomHistory(user.getId(), pageable);
        model.addAttribute("rooms", roomHistory);
        return "room-history";
    }

    @GetMapping("/update")
    public String showUpdateProfile(Model model, @AuthenticationPrincipal User user){
        if(!model.containsAttribute("userUpdateForm")){
            User dbUser = userService.getUserById(user.getId());
            UserUpdateForm form = new UserUpdateForm();
            form.setFirstName(dbUser.getFirstName());
            form.setLastName(dbUser.getLastName());
            model.addAttribute("userUpdateForm", form);
        }
        return "update-profile";
    }

    @ValidateFormWithPRG(formName = "userUpdateForm",redirectUrlOnError = "'redirect:/profile/update'")
    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("userUpdateForm")UserUpdateForm form, BindingResult bindingResult, RedirectAttributes attributes,
                                @AuthenticationPrincipal User user){
        userService.updateProfile(form, user.getId());
        return "redirect:/profile";
    }

    @Autowired
    public ProfileController(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }
}
