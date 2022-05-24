package com.example.epamhotelspring.controller.admin;

import com.example.epamhotelspring.forms.CloseRoomForm;
import com.example.epamhotelspring.service.admin.AdminRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminRoomController {

    private final AdminRoomService roomService;

    //TODO make sure this is for admin only
    @PostMapping("/room/close")
    public String closeRoom(@Valid @ModelAttribute("closeRoomForm") CloseRoomForm closeRoomForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        roomService.closeRoom(closeRoomForm);
        return "redirect:/";
    }

    @Autowired
    public AdminRoomController(AdminRoomService roomService) {
        this.roomService = roomService;
    }
}
