package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.forms.BookRoomForm;
import com.example.epamhotelspring.model.RoomRegistry;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.RoomService;
import com.example.epamhotelspring.validation.utils.FlashAttributePrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;


@Controller
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/")
    public String mainPage(Model model){
        model.addAttribute("rooms", roomService.getAllRooms());
        return "index";
    }

    @GetMapping("/room/{id}")
    public String roomDetail(Model model, @PathVariable Long id){
        RoomDetailDTO room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        if(!model.containsAttribute("bookRoomForm")) {
            model.addAttribute("bookRoomForm", new BookRoomForm());
        }
        return "room";
    }

    @PostMapping("/room/book-room")
    public String bookRoom(
            @Valid @ModelAttribute("bookRoomForm") BookRoomForm bookRoomForm, BindingResult bindingResult,
            RedirectAttributes attrs, @RequestHeader("Referer") String referer, @AuthenticationPrincipal User user){
        FlashAttributePrg errorsPrg = new FlashAttributePrg(bindingResult, attrs, "bookRoomForm", bookRoomForm);
        boolean hasErrors = errorsPrg.processErrorsIfExists();
        if(hasErrors){
            return "redirect:".concat(referer);
        }
        roomService.bookRoom(new RoomRegistry(bookRoomForm), user, bindingResult);

        boolean hasErrorsAfterService = errorsPrg.processErrorsIfExists();
        if(hasErrorsAfterService){
            return "redirect:".concat(referer);
        }
        return "redirect:/profile/room-history";
    }

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
}
