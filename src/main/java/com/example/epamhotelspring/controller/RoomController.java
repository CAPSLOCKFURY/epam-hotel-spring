package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.aop.ValidateFormWithPRG;
import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.forms.BookRoomForm;
import com.example.epamhotelspring.forms.CloseRoomForm;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.service.RoomService;
import com.example.epamhotelspring.service.utils.ServiceErrors;
import com.example.epamhotelspring.validation.utils.FlashAttributePrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/")
    public String mainPage(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        model.addAttribute("rooms", roomService.getAllRooms(pageable));
        return "index";
    }

    @GetMapping("/room/{id}")
    public String roomDetail(Model model, @PathVariable Long id){
        RoomDetailDTO room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        if(!model.containsAttribute("bookRoomForm")) {
            model.addAttribute("bookRoomForm", new BookRoomForm());
        }
        model.addAttribute("closeRoomForm", new CloseRoomForm());
        return "room";
    }

    @ValidateFormWithPRG(redirectUrlOnError = "'redirect:'+#referer", formName = "bookRoomForm")
    @PostMapping("/room/book-room")
    public String bookRoom(
            @Valid @ModelAttribute("bookRoomForm") BookRoomForm bookRoomForm, BindingResult bindingResult,
            RedirectAttributes attrs, @RequestHeader("Referer") String referer, @AuthenticationPrincipal User user){

        ServiceErrors serviceErrors = new ServiceErrors();
        roomService.bookRoom(bookRoomForm, user, serviceErrors);
        FlashAttributePrg errorsPrg = new FlashAttributePrg(serviceErrors.toBindingResult(bindingResult), attrs, "bookRoomForm", bookRoomForm);
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
