package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.aop.ValidateFormWithPRG;
import com.example.epamhotelspring.dto.RoomClassTranslationDTO;
import com.example.epamhotelspring.dto.RoomRequestDTO;
import com.example.epamhotelspring.forms.RoomRequestForm;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.RoomClassTranslationService;
import com.example.epamhotelspring.service.RoomRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class RoomRequestController {

    private final RoomRequestService roomRequestService;

    private final RoomClassTranslationService roomClassTranslationService;

    @GetMapping("/profile/request-room")
    public String showRoomRequestForm(Model model, Locale locale){
        if(!model.containsAttribute("roomRequestForm")) {
            model.addAttribute("roomRequestForm", new RoomRequestForm());
        }
        List<RoomClassTranslationDTO> roomClasses = roomClassTranslationService.getRoomClassesByLanguage(locale.toLanguageTag());
        model.addAttribute("roomClasses", roomClasses);
        return "request-room";
    }

    @ValidateFormWithPRG(formName = "roomRequestForm", redirectUrlOnError = "redirect:/profile/request-room")
    @PostMapping("/profile/request-room")
    public String requestRoom(@Valid @ModelAttribute("roomRequestForm") RoomRequestForm roomRequestForm,
                              BindingResult bindingResult, RedirectAttributes attrs,
                              @AuthenticationPrincipal User user){
        RoomRequest roomRequest = new RoomRequest(roomRequestForm);
        roomRequest.setUser(user);
        roomRequestService.createRoomRequest(roomRequest);
        return "redirect:/profile/room-requests";
    }

    @GetMapping("/profile/room-requests")
    public String getMyRoomRequests(Model model, Locale locale, @AuthenticationPrincipal User user){
        List<RoomRequestDTO> roomRequests = roomRequestService.getUserRoomRequests(user.getId(), locale.toLanguageTag());
        model.addAttribute("roomRequests", roomRequests);
        return "room-requests";
    }

    @GetMapping("/profile/room-requests/close/{id}")
    public String closeRoomRequest(@PathVariable Long id, @AuthenticationPrincipal User user){
        roomRequestService.closeRoomRequest(id, user.getId());
        return "redirect:/profile/room-requests";
    }

    @Autowired
    public RoomRequestController(RoomRequestService roomRequestService, RoomClassTranslationService roomClassTranslationService) {
        this.roomRequestService = roomRequestService;
        this.roomClassTranslationService = roomClassTranslationService;
    }
}
